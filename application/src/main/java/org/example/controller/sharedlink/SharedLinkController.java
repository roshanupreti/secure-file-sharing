package org.example.controller.sharedlink;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.service.SharedLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping(path = "api/v1/share")
@RequiredArgsConstructor
@Slf4j
public class SharedLinkController {

    private final SharedLinkService sharedLinkService;

    // Endpoint to send an email with a download link
    @PostMapping("/new")
    public ResponseEntity<URI> shareNewLink(@Valid @RequestBody LinkRequestDto linkRequestDto) {
        final String currentPath = fromCurrentRequestUri().toUriString();
        return new ResponseEntity<>(sharedLinkService.shareLink(linkRequestDto, currentPath), HttpStatus.CREATED);
    }

    // Endpoint that gets invoked when the email link is clicked. This endpoint simply takes in the link id as the
    // path parameter, sets it as 'id' in the model map, eventually returning the specified 'html' file from templates
    // directory. It simply returns a html page with a button, clicking which will send a pin to the recipient's phone
    // number via sms. Prior to that it checks if the link is expired, already accessed or if a PIN has already been
    // sent out.
    @GetMapping("{id}/request-pin-view")
    public ModelAndView getPinRequestView(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("id", id);
        modelAndView.setViewName("pin-request");
        return modelAndView;
    }

    @PostMapping("/{id}/request")
    public RedirectView generateAndReturnPin(
            @PathVariable String id,
            @Valid @RequestBody PinRequestDto pinRequestDto
    ) {

        // Generate and Send PIN
        sharedLinkService.generateAndSendPin(id, pinRequestDto);

        // Prepare redirect URI
        URI location = URI.create(String.format("%s/%s",
                StringUtils.replace(fromCurrentRequestUri().toUriString(), "/request", ""),
                "validate-pin-view"));

        // Redirect to the validate-pin-page
        return new RedirectView(location.toString());
    }

    @GetMapping("/{id}/validate-pin-view")
    public ModelAndView getPinValidationView(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("id", id);
        modelAndView.setViewName("pin-validate");
        return modelAndView;
    }

    @PostMapping("/{id}")
    public RedirectView validatePinAndRedirectToResource(
            @PathVariable String id,
            @RequestBody PinValidationDto pinValidationDto
    ) {
        return new RedirectView(sharedLinkService.validatePinAndRedirectToResource(id, pinValidationDto).toString());
    }
}
