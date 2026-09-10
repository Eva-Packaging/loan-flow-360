package com.loanflow.common.exception;

public class ForbiddenException extends LoanFlowException {
    public ForbiddenException(String message) {
        super(message, ErrorCode.FORBIDDEN);
    }
}
