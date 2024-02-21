package org.example.service.email;

import org.example.dto.EmailDto;

public interface IEmailService {

    /**
     * Send an e-mail using the specified parameters in {@link EmailDto}
     *
     * @param emailDto contains all the parameters required to send the email.
     */
    void sendEmail(EmailDto emailDto);
}
