package com.loanflow.identity.service.impl;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;
import com.loanflow.common.dto.identity.UserSummaryDto;
import com.loanflow.identity.entity.RefreshToken;
import com.loanflow.identity.exception.InvalidRefreshTokenException;
import com.loanflow.identity.repository.RefreshTokenRepository;
import com.loanflow.identity.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenRepository refreshTokenRepository;

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

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        if (refreshToken.isRevoked()
                || refreshToken.getExpiresAt().isBefore(Instant.now())) {

            throw new InvalidRefreshTokenException();
        }

        return new RefreshTokenResponse(
                "stub-access-token",
                "Bearer",
                3600
        );
    }
}