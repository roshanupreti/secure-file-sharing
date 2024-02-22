package org.example.service.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.example.dto.CurrentLinkStatusDto;
import org.example.dto.PinValidationDto;
import org.example.entity.AccessLog;
import org.example.entity.PinLog;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SharedLinkServiceValidator.class})
@ExtendWith(SpringExtension.class)
class SharedLinkServiceValidatorTest {

    @Autowired
    private SharedLinkServiceValidator sharedLinkServiceValidator;

    /**
     * Method under test:
     * {@link SharedLinkServiceValidator#validateConditionsForPinGeneration(CurrentLinkStatusDto)}
     */
    @Test
    void validate_conditions_for_pin_generation_using_empty_params() {
        // Arrange
        ShareLog shareLog = new ShareLog();
        AccessLog accessLog = new AccessLog();
        var currentStatusDto = new CurrentLinkStatusDto(shareLog, accessLog, new PinLog());

        // Act and Assert
        assertThrows(RESTException.class, () -> sharedLinkServiceValidator
                .validateConditionsForPinGeneration(currentStatusDto));
    }

    /**
     * Method under test:
     * {@link SharedLinkServiceValidator#validatePinAndResourceAccessibility(PinValidationDto, CurrentLinkStatusDto)}
     */
    @Test
    void validate_pin_and_resource_accessibility_with_empty_pin() {
        // Arrange
        PinValidationDto pinValidationDto = new PinValidationDto("Pin", "b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09");

        ShareLog shareLog = new ShareLog();
        AccessLog accessLog = new AccessLog();
        // Pin log is empty.
        var currentStatusDto = new CurrentLinkStatusDto(shareLog, accessLog, new PinLog());

        // Act and Assert
        assertThrows(RESTException.class,
                () -> sharedLinkServiceValidator.validatePinAndResourceAccessibility(pinValidationDto, currentStatusDto));
    }

    /**
     * Method under test:
     * {@link SharedLinkServiceValidator#validatePinAndResourceAccessibility(PinValidationDto, CurrentLinkStatusDto)}
     */
    @Test
    void validate_pin_and_resource_accessibility_with_different_pins() {
        // Arrange
        PinValidationDto pinValidationDto = new PinValidationDto("Pin", "b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09");

        ShareLog shareLog = mock(ShareLog.class);
        AccessLog accessLog = new AccessLog();
        // Different pins in validation dto and current status dto
        var currentStatusDto = new CurrentLinkStatusDto(shareLog, accessLog, new PinLog("42", "Invalid PIN.",
                "b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09", LocalDate.of(1970, 1, 1).atStartOfDay()));

        // Act and Assert
        assertThrows(RESTException.class,
                () -> sharedLinkServiceValidator.validatePinAndResourceAccessibility(pinValidationDto, currentStatusDto));
    }
}
