package com.loanflow.identity.service;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;
import com.loanflow.common.dto.identity.RefreshTokenResponseDto;
import com.loanflow.common.dto.identity.RefreshTokenRequestDto;
import com.loanflow.identity.dto.RefreshTokenRequest;
import com.loanflow.identity.dto.RefreshTokenResponse;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}