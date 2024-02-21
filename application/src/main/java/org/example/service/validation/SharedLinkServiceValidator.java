package org.example.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.dto.CurrentLinkStatusDto;
import org.example.dto.PinValidationDto;
import org.example.exception.RESTException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@Slf4j
@Component
@Validated
public class SharedLinkServiceValidator implements ISharedLinkServiceValidator {

    /**
     * Validate the conditions for a PIN code generation.
     *
     * @param currentLinkStatusDto represents the current status of share log, in relation with pin and access log.
     */
    @Override
    public void validateConditionsForPinGeneration(CurrentLinkStatusDto currentLinkStatusDto) {
        if (Objects.nonNull(currentLinkStatusDto.pinLog())) {
            throw new RESTException(HttpStatus.BAD_REQUEST, "PIN code already sent out.");
        }
    }

    /**
     * Validate whether the resource is accessible or not.
     *
     * @param pinValidationDto represents the user provided PIN code.
     * @param currentLinkStatusDto represents the current status of share log, in relation with pin and access log.
     */
    @Override
    public void validatePinAndResourceAccessibility(
            PinValidationDto pinValidationDto,
            CurrentLinkStatusDto currentLinkStatusDto) {
        // Validate PIN and client fingerprint
        boolean isPinValid = StringUtils.equals(sha256Hex(pinValidationDto.pin()), currentLinkStatusDto.pinLog().getHashedPin());
        boolean isFingerprintValid = StringUtils.equals(sha256Hex(pinValidationDto.clientFingerPrint()), currentLinkStatusDto.pinLog().getClientFingerPrint());

        if (!isPinValid || !isFingerprintValid) {
            throw new RESTException(HttpStatus.BAD_REQUEST, "Invalid PIN.");
        }

        // Check if the link has already been accessed
        if (Objects.nonNull(currentLinkStatusDto.accessLog())) {
            throw new RESTException(HttpStatus.BAD_REQUEST, "The link has already been accessed.");
        }

        // Check expiration
        if (currentLinkStatusDto.pinLog().getExpirationTimestamp().isBefore(LocalDateTime.now())) {
            throw new RESTException(HttpStatus.BAD_REQUEST, "The link has expired.");
        }

        // Check link validity
        if (BooleanUtils.isFalse(currentLinkStatusDto.shareLog().getIsValid())) {
            throw new RESTException(HttpStatus.BAD_REQUEST, "The link is no longer valid.");
        }
    }
}
