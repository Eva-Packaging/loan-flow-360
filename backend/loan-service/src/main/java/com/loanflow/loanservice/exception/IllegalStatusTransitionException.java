package com.loanflow.loanservice.exception;

import com.loanflow.common.exception.LoanFlowException;
import com.loanflow.common.exception.ErrorCode;
import com.loanflow.loanservice.entity.LoanApplicationStatus;

public class IllegalStatusTransitionException extends LoanFlowException {

    private final LoanApplicationStatus from;
    private final LoanApplicationStatus to;

    public IllegalStatusTransitionException(
            LoanApplicationStatus from,
            LoanApplicationStatus to) {
        super(
                "Illegal status transition from " + from + " to " + to,
                ErrorCode.ILLEGAL_STATUS_TRANSITION
        );

        this.from = from;
        this.to = to;
    }

    public LoanApplicationStatus getFrom() {
        return from;
    }

    public LoanApplicationStatus getTo() {
        return to;
    }
}
