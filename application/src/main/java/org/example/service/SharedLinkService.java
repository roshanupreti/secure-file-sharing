package org.example.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.dao.CurrentLinkStatusDao;
import org.example.dto.CurrentLinkStatusDto;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.entity.AccessLog;
import org.example.entity.PinLog;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.example.jparepository.AccessLogRepository;
import org.example.jparepository.PinLogRepository;
import org.example.jparepository.ShareLogRepository;
import org.example.service.sms.SmsService;
import org.example.service.validation.SharedLinkServiceValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.example.service.helper.KeyUtils.decrypt;
import static org.example.service.helper.KeyUtils.encrypt;
import static org.example.service.helper.OTPGenerator.generateOTP;
import static org.jooq.generated.tables.AccessLog.ACCESS_LOG;
import static org.jooq.generated.tables.PinLog.PIN_LOG;
import static org.jooq.generated.tables.ShareLog.SHARE_LOG;

@Slf4j
@AllArgsConstructor
@Service
public class SharedLinkService {

    private ShareLogRepository shareLogRepository;

    private AccessLogRepository accessLogRepository;

    private PinLogRepository pinLogRepository;

    private CurrentLinkStatusDao currentLinkStatusDao;

    private SharedLinkServiceValidator sharedLinkServiceValidator;

    private EmailService emailService;

    private SmsService smsService;

    public URI shareLink(
            LinkRequestDto linkRequestDto,
            String currentPath
    ) {

        // Add a new share log
        ShareLog shareLog = shareLogRepository.save(new ShareLog(
                linkRequestDto.to(),
                linkRequestDto.phoneNumber(),
                false,
                encrypt(linkRequestDto.body()),
                linkRequestDto.name(),
                linkRequestDto.from()
        ));

        // Prepare a new URI pointing to the newly created share link.
        URI locationURI = URI.create(String.format("%s/%s",
                StringUtils.replace(currentPath, "/new", ""),
                String.format("%s/request-pin-view", shareLog.getId())));


        // Send email and add a new share log, if the email is successfully sent.
        boolean emailSent = emailService.sendEmail(
                linkRequestDto.from(),
                linkRequestDto.to(),
                linkRequestDto.subject(),
                String.valueOf(locationURI)
        );

        if (emailSent) {
            // Update share log with sent status and return URI
            shareLog.setSentStatus(true);
            shareLogRepository.save(shareLog);
            return locationURI;
        } else {
            throw new RESTException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while sending the email. Please try again later.");
        }
    }

    public void generateAndSendPin(
            final String id,
            PinRequestDto pinRequestDto
    ) {

        // Get the current status, using joins to pin and access log.
        CurrentLinkStatusDto currentLinkStatusDto = getCurrentStatus(id);

        // Validate whether the PIN can be generated.
        sharedLinkServiceValidator.validatePinGenerationRequest(currentLinkStatusDto);

        // Generate PIN
        String pin = generateOTP();
        log.info("The pin is: {}", pin);

        // Send SMS
        //smsService.sendSms(currentLinkStatusDto.shareLog().getPhoneNumber(), pin);

        // Write to table.
        pinLogRepository.save(
                new PinLog(
                        id,
                        sha256Hex(pin),
                        sha256Hex(pinRequestDto.clientFingerPrint()),
                        currentLinkStatusDto.shareLog().getCreationTimestamp().plusHours(1))
        );
    }

    public URI validatePinAndRedirectToResource(final String id, PinValidationDto pinValidationDto) {

        // Validate the request first.
        CurrentLinkStatusDto currentLinkStatusDto = getCurrentStatus(id);

        final PinLog pinLog = currentLinkStatusDto.pinLog();
        final ShareLog shareLog = currentLinkStatusDto.shareLog();
        final AccessLog accessLog = currentLinkStatusDto.accessLog();

        // Validate PIN and client fingerprint.
        if (StringUtils.equals(sha256Hex(pinValidationDto.pin()), pinLog.getHashedPin())
                && StringUtils.equals(sha256Hex(pinValidationDto.clientFingerPrint()), pinLog.getClientFingerPrint())
                && !pinLog.getExpirationTimestamp().isBefore(LocalDateTime.now())) {

            // Check if the resource has already been accessed
            sharedLinkServiceValidator.checkIfResourceIsInaccessible(shareLog, accessLog);

            // Add to access log table before returning the actual download link
            accessLogRepository.save(new AccessLog(
                    id,
                    sha256Hex(pinValidationDto.clientFingerPrint()),
                    LocalDateTime.now()));

            return URI.create(decrypt(shareLog.getHashedLink()));

        } else {
            throw new RESTException(
                    HttpStatus.FORBIDDEN,
                    "PIN has either expired or is incorrect. Or, the device is not the same as request device.");
        }
    }

    /**
     * Based on the provided 'id' string, retrieve the current link status. This involves a join of all tables.
     *
     * @param id {@link String} id of the shared link.
     * @return {@link CurrentLinkStatusDto}
     */
    private CurrentLinkStatusDto getCurrentStatus(final String id) {
        return currentLinkStatusDao.getLinkStatusForId(id)
                .map(optionalRecord -> new CurrentLinkStatusDto(
                        optionalRecord.into(SHARE_LOG).into(ShareLog.class),
                        optionalRecord.into(ACCESS_LOG).into(AccessLog.class),
                        optionalRecord.into(PIN_LOG).into(PinLog.class)
                ))
                .orElseThrow(() -> new RESTException(HttpStatus.NOT_FOUND, String.format("Invalid id: %s", id)));
    }
}
