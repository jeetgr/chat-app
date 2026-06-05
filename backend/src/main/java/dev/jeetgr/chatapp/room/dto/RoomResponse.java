package dev.jeetgr.chatapp.room.dto;

import java.time.OffsetDateTime;

public record RoomResponse(
        Long id, //
        String name,
        String createdBy,
        OffsetDateTime createdAt) {}
