package dev.jeetgr.chatapp.presence;

import java.security.Principal;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketPresenceListener {

    private final PresenceService presenceService;

    @EventListener
    public void handleWebSocketConnect(SessionConnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        Principal principal = accessor.getUser();

        if (principal == null) {
            return;
        }

        String email = principal.getName();

        presenceService.userConnected(email);

        log.info("User connected: {}", email);
    }

    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        Principal principal = accessor.getUser();

        if (principal == null) {
            return;
        }

        String email = principal.getName();

        presenceService.userDisconnected(email);

        log.info("User disconnected: {}", email);
    }
}
