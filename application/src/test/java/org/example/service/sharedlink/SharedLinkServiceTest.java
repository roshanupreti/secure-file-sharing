package org.example.service.sharedlink;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;

import org.example.dao.CurrentLinkStatusDao;
import org.example.dto.EmailDto;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.entity.ShareLog;
import org.example.exception.RESTException;
import org.example.jparepository.AccessLogRepository;
import org.example.jparepository.PinLogRepository;
import org.example.jparepository.ShareLogRepository;
import org.example.service.email.IEmailService;
import org.example.service.sms.ISmsService;
import org.example.service.validation.ISharedLinkServiceValidator;
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
class SharedLinkServiceTest {

    @MockBean
    private AccessLogRepository accessLogRepository;

    @MockBean
    private CurrentLinkStatusDao currentLinkStatusDao;

    @MockBean
    private IEmailService iEmailService;

    @MockBean
    private ISharedLinkServiceValidator iSharedLinkServiceValidator;

    @MockBean
    private ISmsService iSmsService;

    @MockBean
    private PinLogRepository pinLogRepository;

    @MockBean
    private ShareLogRepository shareLogRepository;

    @Autowired
    private SharedLinkService sharedLinkService;

    /**
     * Method under test:
     * {@link SharedLinkService#createSharedLink(LinkRequestDto, String)}
     */
    @Test
    void create_shared_link_using_invalid_payloads() {
        // Arrange, Act and Assert

        // 1. Create a payload with invalid current path (it contains space.)
        var payload = new LinkRequestDto("Name", "jane.doe@example.org",
                "alice.liddell@example.org",
                "Hello from the Dreaming Spires",
                "Not all who wander are lost",
                "6625550144");
        assertThrows(RESTException.class, () -> sharedLinkService.createSharedLink(
                payload,"Current Path"));

        // 2. Null payload.
        assertThrows(RESTException.class, () -> sharedLinkService.createSharedLink(null, "/new"));
    }

    /**
     * Method under test:
     * {@link SharedLinkService#createSharedLink(LinkRequestDto, String)}
     */
    @Test
    void create_shared_link() {
        // Arrange
        doNothing().when(iEmailService).sendEmail(Mockito.<EmailDto>any());

        ShareLog shareLog = new ShareLog();
        shareLog.setCreationTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay());
        shareLog.setHashedLink("Hashed Link");
        shareLog.setId("42");
        shareLog.setIsValid(true);
        shareLog.setPhoneNumber("6625550144");
        shareLog.setRecipientEmail("jane.doe@example.org");
        shareLog.setSenderEmail("jane.doe@example.org");
        shareLog.setSenderName("Sender Name");
        shareLog.setSentStatus(true);
        when(shareLogRepository.save(Mockito.<ShareLog>any())).thenReturn(shareLog);

        // Act
        assertNotNull(sharedLinkService.createSharedLink(new LinkRequestDto("Name", "jane.doe@example.org",
                "alice.liddell@example.org","Hello from the Dreaming Spires", "Not all who wander are lost",
                "6625550144"), "/new"));

        // Assert
        verify(iEmailService).sendEmail(Mockito.<EmailDto>any());
        verify(shareLogRepository).save(Mockito.<ShareLog>any());
    }

    /**
     * Method under test:
     * {@link SharedLinkService#createSharedLink(LinkRequestDto, String)}
     */
    @Test
    void create_shared_link_with_invalid_repository() {
        // Arrange
        doNothing().when(iEmailService).sendEmail(Mockito.<EmailDto>any());
        when(shareLogRepository.save(Mockito.<ShareLog>any())).thenThrow(new RESTException("An error occurred"));

        // Act and Assert
        var payload = new LinkRequestDto("Name", "jane.doe@example.org",
                "alice.liddell@example.org", "Hello from the Dreaming Spires", "Not all who wander are lost",
                "6625550144");
        // As the repository is mocked to throw an exception, the service call will do exactly that.
        assertThrows(RESTException.class, () -> sharedLinkService.createSharedLink(payload,"/new"));

        // Verify that email and repository methods were called.
        verify(iEmailService).sendEmail(Mockito.<EmailDto>any());
        verify(shareLogRepository).save(Mockito.<ShareLog>any());
    }

    /**
     * Method under test: {@link SharedLinkService#invalidateSharedLink(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void invalidate_shared_link() {
        // TODO: Complete this test.

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";

        // Act
        this.sharedLinkService.invalidateSharedLink(id);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test:
     * {@link SharedLinkService#getAccessibleLink(String, PinValidationDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void get_accessible_link() {
        // TODO: Complete this test.

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";
        PinValidationDto pinValidationDto = null;

        // Act
        URI actualAccessibleLink = this.sharedLinkService.getAccessibleLink(id, pinValidationDto);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test:
     * {@link SharedLinkService#sendPinCode(String, PinRequestDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void send_pin_code() {
        // TODO: Complete this test.

        // Arrange
        // TODO: Populate arranged inputs
        String id = "";
        PinRequestDto pinRequestDto = null;

        // Act
        this.sharedLinkService.sendPinCode(id, pinRequestDto);

        // Assert
        // TODO: Add assertions on result
    }
}
