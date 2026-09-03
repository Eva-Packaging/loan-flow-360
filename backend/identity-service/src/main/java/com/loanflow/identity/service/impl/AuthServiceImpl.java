package com.loanflow.identity.service.impl;

import com.loanflow.identity.exception.InvalidRefreshTokenException;
import org.springframework.stereotype.Service;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;
import com.loanflow.common.dto.identity.UserSummaryDto;
import com.loanflow.identity.service.AuthService;
import com.loanflow.identity.security.JwtUtil;
import com.loanflow.identity.config.JwtProperties;
import com.loanflow.common.dto.identity.RefreshTokenRequestDto;
import com.loanflow.common.dto.identity.RefreshTokenResponseDto;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    //private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;



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
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {
       /*
        RefreshToken stored = refreshTokenRepository
                .findByTokenValue(request.getRefreshToken())
                .orElseThrow(() -> new InvalidRefreshTokenException(
                        "Refresh token is invalid or expired"));

        if (stored.isRevoked() ||
        stored.getExpiresAt().before(new Timestamp(System.currentTimeMillis()))) {
            throw new InvalidRefreshTokenException("Refresh token is invalid or expired");
        }

        String role = stored.getUser().getRoles().iterator().next().getRoleCode();

        String accessToken = jwtUtil.generateAccessToken(
                stored.getUser().getUsername(), role);

        return RefreshTokenResponseDto.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(jwtProperties.getExpirationMs() / 1000)
                .build();




        */
        return RefreshTokenResponseDto.builder().build();
    }


}