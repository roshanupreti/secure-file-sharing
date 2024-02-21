package org.example.service.validation;

import org.example.dto.CurrentLinkStatusDto;
import org.example.dto.PinValidationDto;

/**
 * Interface validator for shared link services related functionalities.
 */
public interface ISharedLinkServiceValidator {

    /**
     * Validate the conditions for a PIN code generation.
     *
     * @param currentLinkStatusDto represents the current status of share log, in relation with pin and access log.
     */
    void validateConditionsForPinGeneration(CurrentLinkStatusDto currentLinkStatusDto);

    /**
     * Validate whether the resource is accessible or not.
     *
     * @param pinValidationDto represents the user provided PIN code.
     * @param currentLinkStatusDto represents the current status of share log, in relation with pin and access log.
     */
    void validatePinAndResourceAccessibility(PinValidationDto pinValidationDto, CurrentLinkStatusDto currentLinkStatusDto);
}
