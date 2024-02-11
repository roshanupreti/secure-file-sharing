package org.example.dto;

import org.example.entity.AccessLog;
import org.example.entity.PinLog;
import org.example.entity.ShareLog;

/**
 *
 * @param shareLog
 * @param accessLog
 * @param pinLog
 */
public record CurrentLinkStatusDto(ShareLog shareLog, AccessLog accessLog, PinLog pinLog) {
}
