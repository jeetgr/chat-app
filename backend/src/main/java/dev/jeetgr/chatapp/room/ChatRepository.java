package dev.jeetgr.chatapp.room;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByName(String name);
}
