package com.api.user_management_api.controller;

import com.api.user_management_api.dto.JwtResponseDto;
import com.api.user_management_api.dto.LoginDto;
import com.api.user_management_api.dto.UserRegistrationDto;
import com.api.user_management_api.dto.UserResponseDto;
import com.api.user_management_api.service.AuthService;
import com.api.user_management_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(
        summary = "Register a new user",
        description = "Create a new user account with name, email and password. Email must be unique.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
        }
    )
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserResponseDto user = userService.createUser(registrationDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login user",
        description = "Authenticate user with email and password. Returns JWT token for API access.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Invalid email or password")
        }
    )
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        JwtResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}