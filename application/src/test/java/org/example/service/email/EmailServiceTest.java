package org.example.service.email;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.example.dto.EmailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailService.class})
@ExtendWith(SpringExtension.class)
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Method under test: {@link EmailService#sendEmail(EmailDto)}
     */
    @Test
    void send_email() throws MailException {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<SimpleMailMessage>any());

        // Act
        emailService.sendEmail(new EmailDto("jane.doe@example.org", "alice.liddell@example.org",
                "Hello from the Dreaming Spires", "Not all who wander are lost"));

        // Assert
        verify(javaMailSender).send(Mockito.<SimpleMailMessage>any());
    }
}
