package com.loanflow.common.exception;

public class ServiceUnavailableException extends LoanFlowException {
    public ServiceUnavailableException(String message) {
        super(message, ErrorCode.SERVICE_UNAVAILABLE);
    }
}
