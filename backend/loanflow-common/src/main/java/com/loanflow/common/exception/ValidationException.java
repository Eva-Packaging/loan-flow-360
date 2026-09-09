package com.loanflow.common.exception;

public class ValidationException extends LoanFlowException {
    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_FAILED);
    }
}
