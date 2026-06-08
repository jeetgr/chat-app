package dev.jeetgr.chatapp.websocket;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import dev.jeetgr.chatapp.security.JwtService;
import dev.jeetgr.chatapp.user.User;
import dev.jeetgr.chatapp.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            List<String> authorization = accessor.getNativeHeader("Authorization");

            if (authorization == null || authorization.isEmpty()) {

                log.warn("Missing websocket authorization header");

                return message;
            }

            String bearerToken = authorization.get(0);

            if (!bearerToken.startsWith("Bearer ")) {

                log.warn("Invalid websocket bearer token");

                return message;
            }

            String jwt = bearerToken.substring(7);

            String email = jwtService.extractEmail(jwt);

            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {

                log.warn("Websocket user not found");

                return message;
            }

            boolean valid = jwtService.isTokenValid(jwt, user.getEmail());

            if (!valid) {

                log.warn("Invalid websocket jwt token");

                return message;
            }

            Principal principal = () -> user.getEmail();

            accessor.setUser(principal);

            accessor.getSessionAttributes().put("user", user);

            log.debug("Authenticated websocket user: {}", email);
        }

        return message;
    }
}
