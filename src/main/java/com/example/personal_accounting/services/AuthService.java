package com.example.personal_accounting.services;

import com.example.personal_accounting.dto.User.CreateUserDto;
import com.example.personal_accounting.dto.JwtRequest;
import com.example.personal_accounting.dto.JwtResponse;
import com.example.personal_accounting.exceptions.InvalidLoginCredentials;
import com.example.personal_accounting.exceptions.UserAlreadyExistsException;
import com.example.personal_accounting.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidLoginCredentials("Incorrect login or password");
        }

        User user = userService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authRequest.getUsername()));

        String token = jwtService.generateToken(user.getId(), user.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with the specified username already exists");
        }
        User user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
