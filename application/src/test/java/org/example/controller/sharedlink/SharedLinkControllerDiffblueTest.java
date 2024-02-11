package org.example.controller.sharedlink;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;

import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.service.SharedLinkService;
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

@ContextConfiguration(classes = {SharedLinkController.class})
@ExtendWith(SpringExtension.class)
class SharedLinkControllerDiffblueTest {
    @Autowired
    private SharedLinkController sharedLinkController;

    @MockBean
    private SharedLinkService sharedLinkService;

    /**
     * Method under test:
     * {@link SharedLinkController#generateAndReturnPin(String, PinRequestDto)}
     */
    @Test
    void testGenerateAndReturnPin() throws Exception {
        // Arrange
        doNothing().when(sharedLinkService).generateAndSendPin(Mockito.<String>any(), Mockito.<PinRequestDto>any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42/request")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new PinRequestDto("b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09")));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/api/v1/share/42/validate-pin-view"));
    }

    /**
     * Method under test:
     * {@link SharedLinkController#validatePinAndRedirectToResource(String, PinValidationDto)}
     */
    @Test
    void testValidatePinAndRedirectToResource() throws Exception {
        // Arrange
        when(sharedLinkService.validatePinAndRedirectToResource(Mockito.<String>any(), Mockito.<PinValidationDto>any()))
                .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
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

    /**
     * Method under test:
     * {@link SharedLinkController#generateAndReturnPin(String, PinRequestDto)}
     */
    @Test
    void testGenerateAndReturnPin2() throws Exception {
        // Arrange
        doNothing().when(sharedLinkService).generateAndSendPin(Mockito.<String>any(), Mockito.<PinRequestDto>any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42/request")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new PinRequestDto("")));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link SharedLinkController#generateAndReturnPin(String, PinRequestDto)}
     */
    @Test
    void testGenerateAndReturnPin3() throws Exception {
        // Arrange
        doNothing().when(sharedLinkService).generateAndSendPin(Mockito.<String>any(), Mockito.<PinRequestDto>any());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/share/42/request");
        postResult.characterEncoding("UTF-8");
        MockHttpServletRequestBuilder contentTypeResult = postResult.contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new PinRequestDto("b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09")));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(sharedLinkController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/api/v1/share/42/validate-pin-view"));
    }

    /**
     * Method under test: {@link SharedLinkController#getPinRequestView(String)}
     */
    @Test
    void testGetPinRequestView() throws Exception {
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
    void testGetPinRequestView2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/share/42/request-pin-view");
        requestBuilder.contentType("https://example.org/example");

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
    void testGetPinValidationView() throws Exception {
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
    void testGetPinValidationView2() throws Exception {
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
     * Method under test: {@link SharedLinkController#shareNewLink(LinkRequestDto)}
     */
    @Test
    void testShareNewLink() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/new")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
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
    void testShareNewLink2() throws Exception {
        // Arrange
        when(sharedLinkService.shareLink(Mockito.<LinkRequestDto>any(), Mockito.<String>any()))
                .thenReturn(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/new")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
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
}
