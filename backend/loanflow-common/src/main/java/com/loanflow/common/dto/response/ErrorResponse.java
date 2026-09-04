package com.loanflow.common.dto.response;

import com.loanflow.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Builder
@Getter
public class ErrorResponse {
    @Builder.Default
    private boolean success = false;
    private String errorCode;
    private String message;
    @Nullable
    private String path;
    private LocalDateTime timestamp;

    public static ErrorResponse of(ErrorCode errorCode, String message, @Nullable String path) {
        return ErrorResponse.builder()
                .errorCode(errorCode.name())
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }
}
