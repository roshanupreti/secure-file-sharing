package org.example.service.sharedlink;

import org.example.dto.LinkRequestDto;
import org.example.dto.PinRequestDto;
import org.example.dto.PinValidationDto;
import org.example.entity.ShareLog;

import java.net.URI;

public interface ISharedLinkService {

    /**
     * @param linkRequestDto contains values to create a {@link ShareLog} with.
     * @param currentPath contains the path, as in from the current request.
     * @return {@link URI} via which the access will ve granted via PIN authentication.
     */
    URI createSharedLink(LinkRequestDto linkRequestDto, String currentPath);

    /**
     * @param id to invalidate.
     */
    void invalidateSharedLink(String id);

    /**
     * @param id shared log identifier.
     * @param pinValidationDto contains user provided PIN and client finger-print.
     * @return {@link URI} accessible link for {@link ShareLog} with the provided id.
     */
    URI getAccessibleLink(String id, PinValidationDto pinValidationDto);

    /**
     * @param id of the shared resource.
     * @param pinRequestDto {@link PinRequestDto} contains requesting device's identifier of some kind.
     */
    void sendPinCode(String id, PinRequestDto pinRequestDto);

}
