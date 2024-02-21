package org.example.controller.sharedlink;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.service.sharedlink.ISharedLinkService;
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

    private final ISharedLinkService sharedLinkService;

    @Operation(
            summary = "Send an email containing the access-link.",
            description = "Based on the provided parameters in the payload, send an email to the recipient, containing" +
                    " the access-link. The payload includes sender and recipient details, including the recipient's" +
                    " phone number which would be used later, for 2FA."
    )
    @PostMapping("/new")
    public ResponseEntity<URI> shareNewLink(@Valid @RequestBody LinkRequestDto linkRequestDto) {
        //final String currentPath = fromCurrentRequestUri().toUriString();
        //final ShareLog shareLog = sharedLinkService.createSharedLink(linkRequestDto);
        /*URI location = URI.create(String.format("%s/%s",
                StringUtils.replace(currentPath, "/new", ""),
                String.format("%s/request-pin-view", shareLog.getId())));*/
        return new ResponseEntity<>(sharedLinkService.createSharedLink(linkRequestDto, fromCurrentRequestUri().toUriString()), HttpStatus.CREATED);
        //return new ResponseEntity<>(service.shareLink(linkRequestDto, currentPath), HttpStatus.CREATED);
    }


    @GetMapping("{id}/request-pin-view")
    public ModelAndView getPinRequestView(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModelMap().addAttribute("id", id);
        modelAndView.setViewName("pin-request");
        return modelAndView;
    }

    @Operation(
            summary = "Send the PIN code to the recipient's phone number, via SMS.",
            description = "Invocation of this endpoint results in the sending of the PIN code to the recipient," +
                    " provided that the associated link hasn't expired or been accessed."
    )
    @PostMapping("/{id}/request")
    public RedirectView generateAndReturnPin(
            @PathVariable String id,
            @Valid @RequestBody PinRequestDto pinRequestDto
    ) {

        // Generate and Send PIN
        //service.generateAndSendPin(id, pinRequestDto);

        sharedLinkService.sendPinCode(id, pinRequestDto);

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

    @Operation(
            summary = "Validate the PIN and redirect to the shared resource.",
            description = "This endpoint takes in the PIN from payload, validates it and redirects to the shared" +
                    " resource, provided that the PIN is valid."
    )
    @PostMapping("/{id}")
    public RedirectView validatePinAndRedirectToResource(
            @PathVariable String id,
            @RequestBody PinValidationDto pinValidationDto
    ) {
        return new RedirectView(sharedLinkService.getAccessibleLink(id, pinValidationDto).toString());
    }

    @Operation(
            summary = "Invalidate a single shared-resource.",
            description = "Invalidate a single resource, based on the provided 'id' and payload."
    )
    @PostMapping("/{id}/invalidate")
    public void invalidateSharedResource(@PathVariable String id) {
        sharedLinkService.invalidateSharedLink(id);
    }
}
