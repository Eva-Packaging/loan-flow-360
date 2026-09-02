package com.loanflow.common.exception;

public class AccessDeniedException extends LoanFlowException {
    public AccessDeniedException(String message) {
        super(message, ErrorCode.FORBIDDEN);
    }
}
