package com.loanflow.identity.dto;

public class RefreshTokenResponse {

    private String accessToken;
    private String tokenType;
    private long expiresIn;

    public RefreshTokenResponse(
            String accessToken,
            String tokenType,
            long expiresIn) {

        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}