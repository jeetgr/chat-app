package dev.jeetgr.chatapp.websocket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChatMessage(
        @NotNull Long roomId, //
        @NotBlank @Size(max = 5000) String content) {}
