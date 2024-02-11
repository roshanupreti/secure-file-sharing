package org.example.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @param name        Sender's name
 * @param from        Sender's email address
 * @param to          Recipient's email address
 * @param subject     Subject of the email
 * @param body        Body of the email, possibly containing the download link
 * @param phoneNumber Recipient's phone number
 */
public record LinkRequestDto(

        @NotBlank(message = "Sender's name is required.")
        String name,
        @Email(message = "Sender's valid email is required.")
        String from,
        @Email(message = "Recipient's valid email is required.")
        String to,
        @NotBlank(message = "Subject is required.")
        String subject,
        @NotBlank(message = "Body is required.")
        String body,
        @Pattern(regexp = "^(?:\\+?358|0{0,2}358)?\\s?(4\\d|50|457)\\s?-?(\\d{3,4}\\s?-?\\d{1,4})$",
                message = "Invalid Phone number.")
        String phoneNumber) {

    @Override
    public String toString() {
        return "LinkRequestDto{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
