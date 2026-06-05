package dev.jeetgr.chatapp.message;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.message.dto.MessageResponse;
import dev.jeetgr.chatapp.message.dto.SendMessageRequest;
import dev.jeetgr.chatapp.user.User;

@RestController
@RequestMapping("/rooms/{roomId}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(
            @PathVariable Long roomId,
            @Valid @RequestBody SendMessageRequest request,
            @AuthenticationPrincipal User currentUser) {

        return messageService.sendMessage(roomId, request, currentUser);
    }
}
