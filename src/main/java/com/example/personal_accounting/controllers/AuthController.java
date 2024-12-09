package com.example.personal_accounting.controllers;

import com.example.personal_accounting.dto.User.CreateUserDto;
import com.example.personal_accounting.dto.JwtRequest;
import com.example.personal_accounting.dto.JwtResponse;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid CreateUserDto userDto) {
        return authService.createUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        return authService.createAuthToken(jwtRequest);
    }
}
