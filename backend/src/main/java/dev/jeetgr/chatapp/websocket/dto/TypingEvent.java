package dev.jeetgr.chatapp.websocket.dto;

import jakarta.validation.constraints.NotNull;

public record TypingEvent(
        @NotNull Long roomId, //
        boolean typing) {}
