package com.subhajit.microservices.user_service.controller;

import com.subhajit.microservices.user_service.dto.AuthenticationCredentialsRequestDTO;
import com.subhajit.microservices.user_service.dto.UserRequestDTO;
import com.subhajit.microservices.user_service.dto.UserResponseDTO;
import com.subhajit.microservices.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO user){
        UserResponseDTO userResponse = userService.registerUser(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthenticationCredentialsRequestDTO authenticationCredentialsRequestDTO){
        String token = userService.loginUser(authenticationCredentialsRequestDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body("User logged in successfully");
    }
}
