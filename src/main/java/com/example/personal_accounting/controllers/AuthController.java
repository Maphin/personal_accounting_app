package com.example.personal_accounting.controllers;

import com.example.personal_accounting.dto.User.CreateUserDto;
import com.example.personal_accounting.dto.JwtRequest;
import com.example.personal_accounting.dto.JwtResponse;
import com.example.personal_accounting.dto.User.UserDto;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserDto> register(@RequestBody @Valid CreateUserDto userDto) {
        UserDto user = authService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        return authService.createAuthToken(jwtRequest);
    }
}
