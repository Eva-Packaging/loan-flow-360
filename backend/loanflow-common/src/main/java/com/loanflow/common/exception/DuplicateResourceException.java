package com.loanflow.common.exception;

public class DuplicateResourceException extends LoanFlowException {
    public DuplicateResourceException(String message) {
        super(message, ErrorCode.DUPLICATE_RESOURCE);
    }
}
