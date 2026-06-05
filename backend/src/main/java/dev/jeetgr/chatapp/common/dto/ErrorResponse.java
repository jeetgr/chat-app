package dev.jeetgr.chatapp.common.dto;

import java.time.OffsetDateTime;

public record ErrorResponse(OffsetDateTime timestamp, int status, String error) {}
