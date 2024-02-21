package org.example.service.sharedlink;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.CurrentLinkStatusDao;
import org.example.dto.*;
import org.example.entity.AccessLog;
import org.example.entity.PinLog;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.example.jparepository.AccessLogRepository;
import org.example.jparepository.PinLogRepository;
import org.example.jparepository.ShareLogRepository;
import org.example.service.email.IEmailService;
import org.example.service.sms.ISmsService;
import org.example.service.validation.ISharedLinkServiceValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.example.utils.KeyUtils.decrypt;
import static org.example.utils.KeyUtils.encrypt;
import static org.example.utils.OTPGenerator.generateOTP;
import static org.jooq.generated.tables.AccessLog.ACCESS_LOG;
import static org.jooq.generated.tables.PinLog.PIN_LOG;
import static org.jooq.generated.tables.ShareLog.SHARE_LOG;

@AllArgsConstructor
@Service
public class SharedLinkService implements ISharedLinkService {

    private final IEmailService emailService;

    private final ISmsService smsService;

    private final ShareLogRepository shareLogRepository;

    private final CurrentLinkStatusDao currentLinkStatusDao;

    private final ISharedLinkServiceValidator sharedLinkServiceValidator;

    private final PinLogRepository pinLogRepository;

    private final AccessLogRepository accessLogRepository;

    /**
     * @param request contains values to create a {@link ShareLog} with.
     * @param currentPath contains the path, as in from the current request.
     * @return {@link URI} via which the access will ve granted via PIN authentication.
     */
    @Override
    public URI createSharedLink(
            LinkRequestDto request,
            String currentPath) {
        // Send e-mail and persist.
        try {
            final String id = UUID.randomUUID().toString();
            URI location = URI.create(String.format("%s/%s",
                    StringUtils.replace(currentPath, "/new", ""),
                    String.format("%s/request-pin-view", id)));
            emailService.sendEmail(new EmailDto(request.from(), request.to(), request.subject(), location.toString()));

            shareLogRepository.save(new ShareLog(
                    id,
                    request.to(),
                    request.phoneNumber(),
                    true,
                    encrypt(request.body()),
                    request.name(),
                    request.from(),
                    true
            ));

            return location;
        } catch (Exception e) {
            throw new RESTException(e.getMessage());
        }
    }

    /**
     * @param id to invalidate.
     */
    @Override
    public void invalidateSharedLink(String id) {
        ShareLog shareLog = getCurrentLinkStatus(id).shareLog();
        shareLog.setIsValid(false);
        shareLogRepository.save(shareLog);
    }

    /**
     * @param id shared log identifier.
     * @param pinValidationDto contains user provided PIN and client finger-print.
     * @return {@link URI} accessible link for {@link ShareLog} with the provided id.
     */
    @Override
    public URI getAccessibleLink(String id, PinValidationDto pinValidationDto) {
        CurrentLinkStatusDto currentLinkStatusDto = getCurrentLinkStatus(id);
        sharedLinkServiceValidator.validatePinAndResourceAccessibility(pinValidationDto, currentLinkStatusDto);

        // Add to access log table before returning the actual download link
        accessLogRepository.save(new AccessLog(
                id,
                sha256Hex(pinValidationDto.clientFingerPrint()),
                LocalDateTime.now()));

        return URI.create(decrypt(currentLinkStatusDto.shareLog().getHashedLink()));
    }

    /**
     * @param id            of the shared resource.
     * @param pinRequestDto {@link PinRequestDto} contains requesting device's identifier of some kind.
     */
    @Override
    public void sendPinCode(String id, PinRequestDto pinRequestDto) {
        CurrentLinkStatusDto currentStatus = getCurrentLinkStatus(id);

        // Validate before generating the PIN
        sharedLinkServiceValidator.validateConditionsForPinGeneration(currentStatus);

        try {
            final String pin = generateOTP();
            smsService.sendSMS(currentStatus.shareLog().getPhoneNumber(), pin);
            // Persist to pin_log table.
            pinLogRepository.save(
                    new PinLog(
                            id,
                            sha256Hex(pin),
                            sha256Hex(pinRequestDto.clientFingerPrint()),
                            currentStatus.shareLog().getCreationTimestamp().plusHours(1))
            );
        } catch (Exception e) {
            throw new RESTException(e.getMessage());
        }
    }

    /**
     * @param id of the shared link resource.
     * @return current status of the shared link, in adjunct with access and pin logs.
     */
    private CurrentLinkStatusDto getCurrentLinkStatus(final String id) {
        return currentLinkStatusDao.getLinkStatusForId(id)
                .map(r -> new CurrentLinkStatusDto(
                        Objects.nonNull(r.get(SHARE_LOG.ID)) ? r.into(SHARE_LOG).into(ShareLog.class) : null,
                        Objects.nonNull(r.get(ACCESS_LOG.ID)) ? r.into(ACCESS_LOG).into(AccessLog.class) : null,
                        Objects.nonNull(r.get(PIN_LOG.ID)) ? r.into(PIN_LOG).into(PinLog.class) : null))
                .orElseThrow(() -> new RESTException(HttpStatus.NOT_FOUND, String.format("Invalid resource id: %s", id)));
    }
}
