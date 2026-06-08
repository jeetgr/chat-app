package dev.jeetgr.chatapp.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisMessagePublisher {

    private static final String CHAT_TOPIC = "chat-messages";

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(RedisChatMessage message) {
        redisTemplate.convertAndSend(CHAT_TOPIC, message);
    }
}
