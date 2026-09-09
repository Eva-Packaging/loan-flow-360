package com.loanflow.common.dto.identity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponseDto {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
