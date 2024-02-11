package org.example.dto;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @param clientFingerPrint MAC id of the device from where the request is originating.
 */
public record PinRequestDto(@NotBlank(message = "Unique client id is required") String clientFingerPrint) {

    @Override
    public String toString() {
        return "PinRequestDto{" +
                "clientFingerPrint='" + clientFingerPrint + '\'' +
                '}';
    }
}
