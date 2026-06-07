package dev.jeetgr.chatapp.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.jeetgr.chatapp.message.MessageService;
import dev.jeetgr.chatapp.message.dto.MessageResponse;
import dev.jeetgr.chatapp.user.User;
import dev.jeetgr.chatapp.websocket.dto.ChatMessage;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message, Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        MessageResponse savedMessage = messageService.sendRealtimeMessage(message, currentUser);

        messagingTemplate.convertAndSend("/topic/rooms/" + message.roomId(), savedMessage);

        log.debug("Broadcasted message to room {}", message.roomId());
    }
}
