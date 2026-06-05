package dev.jeetgr.chatapp.user.dto;

import java.time.OffsetDateTime;

public record CurrentUserResponse(Long id, String email, OffsetDateTime createdAt) {}
