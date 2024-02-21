package org.example.dto;

/**
 * Represent an E-mail DTO
 *
 * @param from sender
 * @param to recipient
 * @param subject e-mail subject
 * @param body e-mail content
 */
public record EmailDto(String from, String to, String subject, String body) {
}
