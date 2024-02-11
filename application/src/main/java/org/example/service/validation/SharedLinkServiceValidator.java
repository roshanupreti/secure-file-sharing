package org.example.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.example.dto.CurrentLinkStatusDto;
import org.example.entity.AccessLog;
import org.example.entity.PinLog;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
@Validated
public class SharedLinkServiceValidator {

    public void validatePinGenerationRequest(CurrentLinkStatusDto currentLinkStatusDto) {
        final ShareLog shareLog = currentLinkStatusDto.shareLog();
        final AccessLog accessLog = currentLinkStatusDto.accessLog();
        final PinLog pinLog = currentLinkStatusDto.pinLog();

        if (Objects.isNull(pinLog)
                || Objects.isNull(pinLog.getId())) {
            checkIfResourceIsInaccessible(shareLog, accessLog);
        } else {
            throw new RESTException(
                    HttpStatus.BAD_REQUEST,
                    "A pin code for this link has already been sent out."
            );
        }
    }

    public void checkIfResourceIsInaccessible(ShareLog shareLog, AccessLog accessLog) {
        if (Duration.between(shareLog.getCreationTimestamp(), LocalDateTime.now()).toHours() > 1
                || BooleanUtils.isFalse(shareLog.getSentStatus())
                || (Objects.nonNull(accessLog) && Objects.nonNull(accessLog.getId()))) {
            throw new RESTException(
                    HttpStatus.NOT_FOUND,
                    String.format("Link Inaccessible - It could've been accessed, expired or not sent at all in " +
                            "the first place. Please contact the sender: %s using the email: %s",
                            shareLog.getSenderName(),
                            shareLog.getSenderEmail())
            );
        }
    }
}
