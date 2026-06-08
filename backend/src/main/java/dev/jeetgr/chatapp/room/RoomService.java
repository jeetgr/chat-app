package dev.jeetgr.chatapp.room;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.common.exception.AlreadyRoomMemberException;
import dev.jeetgr.chatapp.common.exception.RoomAlreadyExistsException;
import dev.jeetgr.chatapp.common.exception.RoomMembershipNotFoundException;
import dev.jeetgr.chatapp.common.exception.RoomNotFoundException;
import dev.jeetgr.chatapp.room.dto.CreateRoomRequest;
import dev.jeetgr.chatapp.room.dto.RoomResponse;
import dev.jeetgr.chatapp.user.User;
import dev.jeetgr.chatapp.websocket.dto.RoomPresenceEvent;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final SimpMessagingTemplate messagingTemplate;

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
                savedRoom.getId(), //
                savedRoom.getName(),
                savedRoom.getCreatedBy().getEmail(),
                savedRoom.getCreatedAt());
    }

    public List<RoomResponse> getRooms() {

        return chatRoomRepository.findAll().stream()
                .map(room -> new RoomResponse(
                        room.getId(), //
                        room.getName(),
                        room.getCreatedBy().getEmail(),
                        room.getCreatedAt()))
                .toList();
    }

    public void joinRoom(Long roomId, User currentUser) {

        ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));

        boolean alreadyMember = roomMemberRepository.existsByRoomIdAndUserId(roomId, currentUser.getId());

        if (alreadyMember) {
            throw new AlreadyRoomMemberException();
        }

        RoomMember roomMember = RoomMember.builder()
                .room(room)
                .user(currentUser)
                .joinedAt(OffsetDateTime.now())
                .build();

        roomMemberRepository.save(roomMember);

        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId + "/presence",
                new RoomPresenceEvent(roomId, currentUser.getEmail(), "JOINED"));
    }

    @Transactional
    public void leaveRoom(Long roomId, User currentUser) {

        boolean membershipExists = roomMemberRepository.existsByRoomIdAndUserId(roomId, currentUser.getId());

        if (!membershipExists) {
            throw new RoomMembershipNotFoundException();
        }

        roomMemberRepository.deleteByRoomIdAndUserId(roomId, currentUser.getId());

        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId + "/presence", //
                new RoomPresenceEvent(roomId, currentUser.getEmail(), "LEFT"));
    }
}
