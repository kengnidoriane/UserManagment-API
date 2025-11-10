package com.api.user_management_api.controller;

import com.api.user_management_api.dto.UserResponseDto;
import com.api.user_management_api.dto.UserUpdateDto;
import com.api.user_management_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "User CRUD operations")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
        summary = "Get all users",
        description = "Retrieve a list of all registered users. Requires JWT authentication.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
        }
    )
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get user by ID",
        description = "Retrieve a specific user by their ID. Requires JWT authentication.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
        }
    )
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update user",
        description = "Update user information. Only provided fields will be updated. Requires JWT authentication.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input or email already exists"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
        }
    )
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, 
                                                     @Valid @RequestBody UserUpdateDto updateDto) {
        UserResponseDto user = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete user",
        description = "Delete a user by their ID. This action cannot be undone. Requires JWT authentication.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required")
        }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}