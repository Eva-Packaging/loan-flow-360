package com.loanflow.identity.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.loanflow.identity.dto.LoginRequestDto;
import com.loanflow.identity.dto.LoginResponseDto;
import com.loanflow.identity.dto.RefreshTokenRequestDto;

@Service
public class AuthService {

    public LoginResponseDto login(LoginRequestDto request) {
        // TODO: Sprint 3 - Implement actual database lookup and JWT generation
        throw new ResponseStatusException(
            HttpStatus.NOT_IMPLEMENTED, 
            "JWT login is slated for Sprint 3"
        );
    }

    public LoginResponseDto refresh(RefreshTokenRequestDto request) {
        // TODO: Sprint 3 - Implement token validation and regeneration
        throw new ResponseStatusException(
            HttpStatus.NOT_IMPLEMENTED, 
            "Token refresh is slated for Sprint 3"
        );
    }
}