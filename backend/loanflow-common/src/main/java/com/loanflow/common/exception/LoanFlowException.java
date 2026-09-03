package com.loanflow.common.exception;

import lombok.Getter;

@Getter
public class LoanFlowException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public LoanFlowException(String message, ErrorCode errorCode) {
        super(message + " (Error Code: " + errorCode + ")");
        this.errorCode = errorCode;
        this.message = message;
    }

    public LoanFlowException(String message) {
        super(message);
        this.message = message;
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }
}
