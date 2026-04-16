package com.loanflow.identity.service.impl;

import org.springframework.stereotype.Service;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;
import com.loanflow.common.dto.identity.UserSummaryDto;
import com.loanflow.identity.service.AuthService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        return LoginResponseDto.builder()
            .accessToken("stub-access-token")
            .tokenType("Bearer")
            .expiresIn(3600)
            .user(UserSummaryDto.builder()
                .id(102L)
                .username(request.getUsername())
                .role("LOAN_OFFICER")
                .build())
            .build();
    }
}