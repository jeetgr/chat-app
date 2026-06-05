package dev.jeetgr.chatapp.message.dto;

import java.time.OffsetDateTime;

public record MessageResponse(
        Long id, //
        Long roomId,
        String sender,
        String content,
        OffsetDateTime createdAt) {}
