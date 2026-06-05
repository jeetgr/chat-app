package dev.jeetgr.chatapp.room;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.room.dto.CreateRoomRequest;
import dev.jeetgr.chatapp.room.dto.RoomResponse;
import dev.jeetgr.chatapp.user.User;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(
            @Valid @RequestBody CreateRoomRequest request, //
            @AuthenticationPrincipal User currentUser) {

        return roomService.createRoom(request, currentUser);
    }

    @GetMapping
    public List<RoomResponse> getRooms() {
        return roomService.getRooms();
    }

    @PostMapping("/{roomId}/join")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinRoom(
            @PathVariable Long roomId, //
            @AuthenticationPrincipal User currentUser) {

        roomService.joinRoom(roomId, currentUser);
    }
}
