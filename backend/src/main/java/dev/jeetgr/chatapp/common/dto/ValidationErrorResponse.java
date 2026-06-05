package dev.jeetgr.chatapp.common.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public record ValidationErrorResponse(OffsetDateTime timestamp, int status, Map<String, String> errors) {}
