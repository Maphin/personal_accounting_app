package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/my-profile")
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal) {
        UserDto user = new UserDto();
        user.setId(principal.userId());
        user.setUsername(principal.username());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
