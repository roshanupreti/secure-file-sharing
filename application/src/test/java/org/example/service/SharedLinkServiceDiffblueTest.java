package org.example.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.example.dao.CurrentLinkStatusDao;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.example.jparepository.AccessLogRepository;
import org.example.jparepository.PinLogRepository;
import org.example.jparepository.ShareLogRepository;
import org.example.service.sms.SmsService;
import org.example.service.validation.SharedLinkServiceValidator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SharedLinkService.class})
@ExtendWith(SpringExtension.class)
class SharedLinkServiceDiffblueTest {
    @MockBean
    private AccessLogRepository accessLogRepository;

    @MockBean
    private CurrentLinkStatusDao currentLinkStatusDao;

    @MockBean
    private EmailService emailService;

    @MockBean
    private PinLogRepository pinLogRepository;

    @MockBean
    private ShareLogRepository shareLogRepository;

    @MockBean
    private SharedLinkServiceValidator sharedLinkServiceValidator;

    @MockBean
    private SmsService smsService;

    @Autowired
    private SharedLinkService sharedLinkService;

    /**
     * Method under test:
     * {@link SharedLinkService#shareLink(LinkRequestDto, String)}
     */
    @Test
    void testShareLink() {
        // Arrange
        ShareLog shareLog = mock(ShareLog.class);
        when(shareLog.getId()).thenReturn("42");
        doNothing().when(shareLog).setCreationTimestamp(Mockito.<LocalDateTime>any());
        doNothing().when(shareLog).setHashedLink(Mockito.<String>any());
        doNothing().when(shareLog).setId(Mockito.<String>any());
        doNothing().when(shareLog).setPhoneNumber(Mockito.<String>any());
        doNothing().when(shareLog).setRecipientEmail(Mockito.<String>any());
        doNothing().when(shareLog).setSenderEmail(Mockito.<String>any());
        doNothing().when(shareLog).setSenderName(Mockito.<String>any());
        doNothing().when(shareLog).setSentStatus(Mockito.<Boolean>any());
        shareLog.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        shareLog.setHashedLink("Hashed Link");
        shareLog.setId("42");
        shareLog.setPhoneNumber("6625550144");
        shareLog.setRecipientEmail("jane.doe@example.org");
        shareLog.setSenderEmail("jane.doe@example.org");
        shareLog.setSenderName("Sender Name");
        shareLog.setSentStatus(true);
        when(shareLogRepository.save(Mockito.<ShareLog>any())).thenReturn(shareLog);
        when(emailService.sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(true);

        // Act
        sharedLinkService.shareLink(new LinkRequestDto("Name", "jane.doe@example.org", "alice.liddell@example.org",
                "Hello from the Dreaming Spires", "Not all who wander are lost", "6625550144"), "AES");

        // Assert
        verify(shareLog).getId();
        verify(shareLog).setCreationTimestamp(Mockito.<LocalDateTime>any());
        verify(shareLog).setHashedLink(Mockito.<String>any());
        verify(shareLog).setId(Mockito.<String>any());
        verify(shareLog).setPhoneNumber(Mockito.<String>any());
        verify(shareLog).setRecipientEmail(Mockito.<String>any());
        verify(shareLog).setSenderEmail(Mockito.<String>any());
        verify(shareLog).setSenderName(Mockito.<String>any());
        verify(shareLog, atLeast(1)).setSentStatus(Mockito.<Boolean>any());
        verify(emailService).sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(shareLogRepository, atLeast(1)).save(Mockito.<ShareLog>any());
    }

    /**
     * Method under test:
     * {@link SharedLinkService#shareLink(LinkRequestDto, String)}
     */
    @Test
    void testShareLink2() {
        // Arrange
        ShareLog shareLog = mock(ShareLog.class);
        when(shareLog.getId()).thenReturn("42");
        doNothing().when(shareLog).setCreationTimestamp(Mockito.<LocalDateTime>any());
        doNothing().when(shareLog).setHashedLink(Mockito.<String>any());
        doNothing().when(shareLog).setId(Mockito.<String>any());
        doNothing().when(shareLog).setPhoneNumber(Mockito.<String>any());
        doNothing().when(shareLog).setRecipientEmail(Mockito.<String>any());
        doNothing().when(shareLog).setSenderEmail(Mockito.<String>any());
        doNothing().when(shareLog).setSenderName(Mockito.<String>any());
        doNothing().when(shareLog).setSentStatus(Mockito.<Boolean>any());
        shareLog.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        shareLog.setHashedLink("Hashed Link");
        shareLog.setId("42");
        shareLog.setPhoneNumber("6625550144");
        shareLog.setRecipientEmail("jane.doe@example.org");
        shareLog.setSenderEmail("jane.doe@example.org");
        shareLog.setSenderName("Sender Name");
        shareLog.setSentStatus(true);
        when(shareLogRepository.save(Mockito.<ShareLog>any())).thenReturn(shareLog);
        when(emailService.sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(false);

        // Act and Assert
        assertThrows(RESTException.class,
                () -> sharedLinkService.shareLink(new LinkRequestDto("Name", "jane.doe@example.org",
                                "alice.liddell@example.org", "Hello from the Dreaming Spires", "Not all who wander are lost", "6625550144"),
                        "AES"));
        verify(shareLog).getId();
        verify(shareLog).setCreationTimestamp(Mockito.<LocalDateTime>any());
        verify(shareLog).setHashedLink(Mockito.<String>any());
        verify(shareLog).setId(Mockito.<String>any());
        verify(shareLog).setPhoneNumber(Mockito.<String>any());
        verify(shareLog).setRecipientEmail(Mockito.<String>any());
        verify(shareLog).setSenderEmail(Mockito.<String>any());
        verify(shareLog).setSenderName(Mockito.<String>any());
        verify(shareLog).setSentStatus(Mockito.<Boolean>any());
        verify(emailService).sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any());
        verify(shareLogRepository).save(Mockito.<ShareLog>any());
    }

    /**
     * Method under test:
     * {@link SharedLinkService#generateAndSendPin(String, PinRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateAndSendPin() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";
        PinRequestDto pinRequestDto = null;

        // Act
        this.sharedLinkService.generateAndSendPin(id, pinRequestDto);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test:
     * {@link SharedLinkService#validatePinAndRedirectToResource(String, PinValidationDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testValidatePinAndRedirectToResource() {
        // TODO: Complete this test.
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";
        PinValidationDto pinValidationDto = null;

        // Act
        URI actualValidatePinAndRedirectToResourceResult = this.sharedLinkService.validatePinAndRedirectToResource(id,
                pinValidationDto);

        // Assert
        // TODO: Add assertions on result
    }
}
