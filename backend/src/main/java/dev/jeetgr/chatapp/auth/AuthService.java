package dev.jeetgr.chatapp.auth;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.auth.dto.RegisterRequest;
import dev.jeetgr.chatapp.user.User;
import dev.jeetgr.chatapp.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .createdAt(OffsetDateTime.now())
                .build();

        userRepository.save(user);
    }
}
