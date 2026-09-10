package com.loanflow.identity.service;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;
import com.loanflow.common.dto.identity.RefreshTokenResponseDto;
import com.loanflow.common.dto.identity.RefreshTokenRequestDto;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);
}