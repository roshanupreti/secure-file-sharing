package org.example.controller.sharedlink;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Paths;
import java.time.LocalDate;

import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.service.sharedlink.ISharedLinkService;
import org.junit.jupiter.api.Disabled;
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
  @MockBean
  private ISharedLinkService iSharedLinkService;

  @Autowired
  private SharedLinkController sharedLinkController;

  /**
   * Method under test:
   * {@link SharedLinkController#generateAndReturnPin(String, PinRequestDto)}
   */
  @Test
  @Disabled("TODO: Complete this test")
  void testGenerateAndReturnPin() throws Exception {
    // TODO: Diffblue Cover was only able to create a partial test for this method:
    //   Reason: No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: org.example.dto.PinRequestDto["expiration"])
    //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
    //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1308)
    //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
    //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:732)
    //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:772)
    //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
    //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:479)
    //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:318)
    //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4719)
    //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3964)
    //   See https://diff.blue/R013 to resolve this issue.

    // Arrange
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/share/42/request")
            .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(
            new PinRequestDto("b6:03:0e:39:97:9e:d0:e7:24:ce:a3:77:3e:01:42:09", LocalDate.of(1970, 1, 1).atStartOfDay())));

    // Act
    MockMvcBuilders.standaloneSetup(sharedLinkController).build().perform(requestBuilder);
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
   * Method under test:
   * {@link SharedLinkController#invalidateSharedResource(String)}
   */
  @Test
  void testInvalidateSharedResource() throws Exception {
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
  void testInvalidateSharedResource2() throws Exception {
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
    when(iSharedLinkService.createSharedLink(Mockito.<LinkRequestDto>any(), Mockito.<String>any()))
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

  /**
   * Method under test:
   * {@link SharedLinkController#validatePinAndRedirectToResource(String, PinValidationDto)}
   */
  @Test
  void testValidatePinAndRedirectToResource() throws Exception {
    // Arrange
    when(iSharedLinkService.getAccessibleLink(Mockito.<String>any(), Mockito.<PinValidationDto>any()))
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
}
