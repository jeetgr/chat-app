package dev.jeetgr.chatapp.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jeetgr.chatapp.user.dto.CurrentUserResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public CurrentUserResponse me(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return new CurrentUserResponse(user.getId(), user.getEmail(), user.getCreatedAt());
    }
}
