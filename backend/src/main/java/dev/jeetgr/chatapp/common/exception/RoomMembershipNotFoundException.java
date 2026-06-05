package dev.jeetgr.chatapp.common.exception;

public class RoomMembershipNotFoundException extends RuntimeException {

    public RoomMembershipNotFoundException() {
        super("User is not a member of this room");
    }
}
