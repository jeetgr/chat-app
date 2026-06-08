package dev.jeetgr.chatapp.websocket;

import java.time.OffsetDateTime;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

import dev.jeetgr.chatapp.common.exception.RoomAccessDeniedException;
import dev.jeetgr.chatapp.common.exception.RoomNotFoundException;
import dev.jeetgr.chatapp.websocket.dto.WebSocketErrorResponse;

@Slf4j
@Controller
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(RoomAccessDeniedException.class)
    @SendToUser("/queue/errors")
    public WebSocketErrorResponse handleRoomAccessDenied(RoomAccessDeniedException ex) {

        log.warn("WebSocket room access denied: {}", ex.getMessage());

        return new WebSocketErrorResponse(OffsetDateTime.now(), ex.getMessage());
    }

    @MessageExceptionHandler(RoomNotFoundException.class)
    @SendToUser("/queue/errors")
    public WebSocketErrorResponse handleRoomNotFound(RoomNotFoundException ex) {

        log.warn("WebSocket room not found: {}", ex.getMessage());

        return new WebSocketErrorResponse(OffsetDateTime.now(), ex.getMessage());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public WebSocketErrorResponse handleGenericException(Exception ex) {

        log.error("Unhandled websocket exception", ex);

        return new WebSocketErrorResponse(OffsetDateTime.now(), "Internal websocket error");
    }
}
