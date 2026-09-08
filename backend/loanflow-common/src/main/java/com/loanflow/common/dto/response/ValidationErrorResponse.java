package com.loanflow.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private List<FieldError> fieldErrors;

    @Builder
    @Getter
    public static class FieldError {
        private String field;
        private String rejectedValue;
        private String message;
    }
}
