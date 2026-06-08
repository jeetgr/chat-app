package dev.jeetgr.chatapp.redis;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedisChatMessage implements Serializable {
    private Long id;
    private Long roomId;
    private String sender;
    private String content;
    private OffsetDateTime createdAt;
}
