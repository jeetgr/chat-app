package dev.jeetgr.chatapp.presence;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class PresenceService {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void userConnected(String email) {
        onlineUsers.add(email);
    }

    public void userDisconnected(String email) {
        onlineUsers.remove(email);
    }

    public boolean isOnline(String email) {
        return onlineUsers.contains(email);
    }

    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}
