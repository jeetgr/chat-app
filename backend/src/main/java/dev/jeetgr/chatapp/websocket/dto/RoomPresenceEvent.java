package dev.jeetgr.chatapp.websocket.dto;

public record RoomPresenceEvent(Long roomId, String user, String type) {}
