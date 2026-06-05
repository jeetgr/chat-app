package dev.jeetgr.chatapp.room;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.common.exception.RoomAlreadyExistsException;
import dev.jeetgr.chatapp.room.dto.CreateRoomRequest;
import dev.jeetgr.chatapp.room.dto.RoomResponse;
import dev.jeetgr.chatapp.user.User;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final ChatRoomRepository chatRoomRepository;

    public RoomResponse createRoom(CreateRoomRequest request, User currentUser) {

        if (chatRoomRepository.findByName(request.name()).isPresent()) {
            throw new RoomAlreadyExistsException(request.name());
        }

        ChatRoom room = ChatRoom.builder()
                .name(request.name())
                .createdBy(currentUser)
                .createdAt(OffsetDateTime.now())
                .build();

        ChatRoom savedRoom = chatRoomRepository.save(room);

        return new RoomResponse(
                savedRoom.getId(), savedRoom.getName(), savedRoom.getCreatedBy().getEmail(), savedRoom.getCreatedAt());
    }
}
