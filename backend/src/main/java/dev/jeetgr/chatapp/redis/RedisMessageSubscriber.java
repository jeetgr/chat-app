package dev.jeetgr.chatapp.redis;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber {

    private final SimpMessagingTemplate messagingTemplate;

    public void receive(RedisChatMessage message) {

        messagingTemplate.convertAndSend("/topic/rooms/" + message.getRoomId(), message);

        log.debug("Redis broadcast delivered to room {}", message.getRoomId());
    }
}
