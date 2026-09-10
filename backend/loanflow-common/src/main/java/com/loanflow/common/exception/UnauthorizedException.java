package com.loanflow.common.exception;

public class UnauthorizedException extends LoanFlowException {
    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
