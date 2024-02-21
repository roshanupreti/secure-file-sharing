package org.example.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 *
 * @param clientFingerPrint some kind of identifier of the device from where the request is originating.
 */
public record PinRequestDto(@NotBlank(message = "Unique client id is required") String clientFingerPrint, LocalDateTime expiration) {

    @Override
    public String toString() {
        return "PinRequestDto{" +
                "clientFingerPrint='" + clientFingerPrint + '\'' +
                '}';
    }
}
