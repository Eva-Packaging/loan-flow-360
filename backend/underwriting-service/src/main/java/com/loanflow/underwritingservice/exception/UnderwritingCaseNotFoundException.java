package com.loanflow.underwritingservice.exception;

public class UnderwritingCaseNotFoundException extends RuntimeException {
    public UnderwritingCaseNotFoundException(Long applicationId) {
        super("Underwriting case not found for application id: " + applicationId);
    }
}
