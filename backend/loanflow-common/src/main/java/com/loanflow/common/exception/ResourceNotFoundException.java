package com.loanflow.common.exception;

public class ResourceNotFoundException extends LoanFlowException {
    public ResourceNotFoundException(String message) {
        super(message, ErrorCode.RESOURCE_NOT_FOUND);
    }
}
