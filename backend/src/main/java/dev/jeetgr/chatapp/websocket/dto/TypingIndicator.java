package dev.jeetgr.chatapp.websocket.dto;

public record TypingIndicator(
        Long roomId, //
        String user,
        boolean typing) {}
