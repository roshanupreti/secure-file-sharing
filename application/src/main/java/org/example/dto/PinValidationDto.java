package org.example.dto;


import jakarta.validation.constraints.NotBlank;

/**
 * @param pin PIN code
 */
public record PinValidationDto(
        @NotBlank(message = "A valid PIN is required.")
        String pin,

        @NotBlank(message = "Client finger print is required.")
        String clientFingerPrint

) {
}
