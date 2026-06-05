package dev.jeetgr.chatapp.message;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.common.exception.RoomAccessDeniedException;
import dev.jeetgr.chatapp.common.exception.RoomNotFoundException;
import dev.jeetgr.chatapp.message.dto.MessageResponse;
import dev.jeetgr.chatapp.message.dto.SendMessageRequest;
import dev.jeetgr.chatapp.room.ChatRoom;
import dev.jeetgr.chatapp.room.ChatRoomRepository;
import dev.jeetgr.chatapp.room.RoomMemberRepository;
import dev.jeetgr.chatapp.user.User;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;

    @Transactional
    public MessageResponse sendMessage(
            Long roomId, //
            SendMessageRequest request,
            User currentUser) {

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));

        boolean isMember = roomMemberRepository.existsByRoomIdAndUserId(roomId, currentUser.getId());

        if (!isMember) {
            throw new RoomAccessDeniedException();
        }

        Message message = Message.builder()
                .room(room)
                .sender(currentUser)
                .content(request.content())
                .createdAt(OffsetDateTime.now())
                .build();

        Message savedMessage = messageRepository.save(message);

        return new MessageResponse(
                savedMessage.getId(),
                room.getId(),
                currentUser.getEmail(),
                savedMessage.getContent(),
                savedMessage.getCreatedAt());
    }

    public List<MessageResponse> getRoomMessages(Long roomId, int page, int size, User currentUser) {

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));

        boolean isMember = roomMemberRepository.existsByRoomIdAndUserId(roomId, currentUser.getId());

        if (!isMember) {
            throw new RoomAccessDeniedException();
        }

        Pageable pageable = PageRequest.of(page, size);

        return messageRepository.findByRoomIdOrderByCreatedAtAsc(room.getId(), pageable).stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        room.getId(),
                        message.getSender().getEmail(),
                        message.getContent(),
                        message.getCreatedAt()))
                .toList();
    }
}
