package dev.jeetgr.chatapp.websocket.dto;

import java.time.OffsetDateTime;

public record WebSocketErrorResponse(OffsetDateTime timestamp, String error) {}
