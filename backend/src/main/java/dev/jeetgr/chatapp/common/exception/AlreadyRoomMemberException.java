package dev.jeetgr.chatapp.common.exception;

public class AlreadyRoomMemberException extends RuntimeException {

    public AlreadyRoomMemberException() {
        super("User is already a room member");
    }
}
