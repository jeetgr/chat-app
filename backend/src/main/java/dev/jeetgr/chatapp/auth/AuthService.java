package dev.jeetgr.chatapp.auth;

import java.time.OffsetDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import dev.jeetgr.chatapp.auth.dto.LoginRequest;
import dev.jeetgr.chatapp.auth.dto.LoginResponse;
import dev.jeetgr.chatapp.auth.dto.RegisterRequest;
import dev.jeetgr.chatapp.auth.dto.RegisterResponse;
import dev.jeetgr.chatapp.common.exception.EmailAlreadyExistsException;
import dev.jeetgr.chatapp.common.exception.InvalidCredentialsException;
import dev.jeetgr.chatapp.security.JwtService;
import dev.jeetgr.chatapp.user.User;
import dev.jeetgr.chatapp.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .createdAt(OffsetDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        return new RegisterResponse(savedUser.getId(), savedUser.getEmail());
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email()).orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordEncoder.matches(request.password(), user.getPasswordHash());

        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}
