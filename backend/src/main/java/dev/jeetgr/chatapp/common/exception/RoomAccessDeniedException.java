package dev.jeetgr.chatapp.common.exception;

public class RoomAccessDeniedException extends RuntimeException {

    public RoomAccessDeniedException() {
        super("User is not a member of this room");
    }
}
