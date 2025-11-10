package com.api.user_management_api.service;

import com.api.user_management_api.dto.JwtResponseDto;
import com.api.user_management_api.dto.LoginDto;
import com.api.user_management_api.dto.UserResponseDto;
import com.api.user_management_api.model.User;
import com.api.user_management_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public JwtResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        UserResponseDto userResponse = new UserResponseDto(user.getId(), user.getName(), user.getEmail());

        return new JwtResponseDto(token, userResponse);
    }
}