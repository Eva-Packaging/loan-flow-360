package com.loanflow.identity.dto;

public record LoginResponseDto(String accessToken, String refreshToken, String tokenType) {
    
    public LoginResponseDto(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, "Bearer");
    }
}