package com.loanflow.identity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanflow.identity.dto.LoginRequestDto;
import com.loanflow.identity.dto.LoginResponseDto;
import com.loanflow.identity.dto.RefreshTokenRequestDto;
import com.loanflow.identity.dto.UserProfileDto;
import com.loanflow.identity.service.AuthService;
import com.loanflow.identity.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // Spring automatically injects the services through this constructor
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponseDto refresh(@Valid @RequestBody RefreshTokenRequestDto request) {
        return authService.refresh(request);
    }

    @GetMapping("/me")
    public UserProfileDto getCurrentUser() {
        return userService.getCurrentUserProfile();
    }
}