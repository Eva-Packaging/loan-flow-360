package com.loanflow.common.exception;

import lombok.Getter;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(404),
    DUPLICATE_RESOURCE(409),
    VALIDATION_FAILED(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    SERVICE_UNAVAILABLE(503),
    INTERNAL_ERROR(500),
    EVENT_PROCESSING_FAILED(500);

    @Getter
    private final int statusCode;

    ErrorCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
