package com.loanflow.identity.service;

import com.loanflow.common.dto.identity.LoginRequestDto;
import com.loanflow.common.dto.identity.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
}