package org.example.controller.sharedlink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.service.sharedlink.ISharedLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {SharedLinkController.class})
@ExtendWith(SpringExtension.class)
class SharedLinkControllerTest {

    @MockBean
    private ISharedLinkService iSharedLinkService;

    @Autowired
    private SharedLinkController sharedLinkController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void sharedLinkControllerTestSetup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Method under test:
     * {@link SharedLinkController#generateAndReturnPin(String, PinRequestDto)}
     */
    @Test
    void generate_and_return_pin() throws Exception {

        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42/request")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper
                .writeValueAsString(new PinRequestDto("b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09", LocalDateTime.now())));

        // Act
        MockMvcBuilders
                .standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    /**
     * Method under test: {@link SharedLinkController#getPinRequestView(String)}
     */
    @Test
    void get_pin_request_view() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/share/42/request-pin-view");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.view().name("pin-request"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("pin-request"));
    }

    /**
     * Method under test: {@link SharedLinkController#getPinRequestView(String)}
     */
    @Test
    void get_pin_request_view_with_uri_variables() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/share/42/request-pin-view",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.view().name("pin-request"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("pin-request"));
    }

    /**
     * Method under test: {@link SharedLinkController#getPinValidationView(String)}
     */
    @Test
    void get_pin_validation_view() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/share/42/validate-pin-view");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.view().name("pin-validate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("pin-validate"));
    }

    /**
     * Method under test: {@link SharedLinkController#getPinValidationView(String)}
     */
    @Test
    void get_pin_validation_view_with_content_type() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/share/42/validate-pin-view");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.view().name("pin-validate"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("pin-validate"));
    }

    /**
     * Method under test:
     * {@link SharedLinkController#invalidateSharedResource(String)}
     */
    @Test
    void invalidate_shared_resource() throws Exception {
        // Arrange
        doNothing().when(iSharedLinkService).invalidateSharedLink(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/share/42/invalidate");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test:
     * {@link SharedLinkController#invalidateSharedResource(String)}
     */
    @Test
    void invalidate_shared_resource_with_content_type() throws Exception {
        // Arrange
        doNothing().when(iSharedLinkService).invalidateSharedLink(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/share/42/invalidate");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link SharedLinkController#shareNewLink(LinkRequestDto)}
     */
    @Test
    void share_new_link_unsuccessfully() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/new")
                .contentType(MediaType.APPLICATION_JSON);

        // Phone number is improperly formatted.
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
                objectMapper.writeValueAsString(new LinkRequestDto("Name", "jane.doe@example.org", "alice.liddell@example.org",
                        "Hello from the Dreaming Spires", "Not all who wander are lost", "6625550144")));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link SharedLinkController#shareNewLink(LinkRequestDto)}
     */
    @Test
    void share_new_link_successfully() throws Exception {
        // Arrange
        when(iSharedLinkService.createSharedLink(Mockito.<LinkRequestDto>any(), Mockito.<String>any()))
                .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/new")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(
                objectMapper.writeValueAsString(new LinkRequestDto("Name", "jane.doe@example.org", "alice.liddell@example.org",
                        "Hello from the Dreaming Spires", "Not all who wander are lost", "358499999999")));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder);

        // Assert
        ResultActions resultActions = actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(String.join("", "<URI>",
                Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString(), "</URI>")));
    }

    /**
     * Method under test:
     * {@link SharedLinkController#validatePinAndRedirectToResource(String, PinValidationDto)}
     */
    @Test
    void validate_pin_and_redirect_to_resource() throws Exception {
        // Arrange
        when(iSharedLinkService.getAccessibleLink(Mockito.<String>any(), Mockito.<PinValidationDto>any()))
                .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42")
                .contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper
                .writeValueAsString(new PinValidationDto("Pin", "b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09")));

        // Act and Assert
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0));
        resultActions.andExpect(MockMvcResultMatchers
                .redirectedUrl(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString()));
    }
}
