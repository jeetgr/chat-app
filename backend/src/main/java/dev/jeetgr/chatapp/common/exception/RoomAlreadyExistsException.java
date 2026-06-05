package dev.jeetgr.chatapp.common.exception;

public class RoomAlreadyExistsException extends RuntimeException {

    public RoomAlreadyExistsException(String roomName) {
        super("Room already exists: " + roomName);
    }
}
