package com.loanflow.loanservice;

import com.loanflow.loanservice.entity.LoanApplicationStatus;
import com.loanflow.loanservice.exception.IllegalStatusTransitionException;
import com.loanflow.loanservice.policy.LoanStatusTransitionPolicy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;



class LoanStatusTransitionPolicyTest {

    @Test
    void shouldAllowSubmittedToUnderReview() {

        LoanStatusTransitionPolicy policy =
                new LoanStatusTransitionPolicy();

        assertTrue(
                policy.isAllowed(
                        LoanApplicationStatus.SUBMITTED,
                        LoanApplicationStatus.UNDER_REVIEW
                )
        );
    }

    @Test
    void shouldThrowForRejectedToSubmitted() {

        LoanStatusTransitionPolicy policy =
                new LoanStatusTransitionPolicy();

        IllegalStatusTransitionException exception =
                assertThrows(
                        IllegalStatusTransitionException.class,
                        () -> policy.validateTransition(
                                LoanApplicationStatus.REJECTED,
                                LoanApplicationStatus.SUBMITTED
                        )
                );

        assertTrue(
                exception.getMessage()
                        .contains("REJECTED")
        );

        assertTrue(
                exception.getMessage()
                        .contains("SUBMITTED")
        );
    }

    @Test
    void shouldThrowWhenFromStatusIsNull() {

        LoanStatusTransitionPolicy policy =
                new LoanStatusTransitionPolicy();

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> policy.isAllowed(
                                null,
                                LoanApplicationStatus.SUBMITTED
                        )
                );

        assertTrue(
                exception.getMessage()
                        .contains("cannot be null")
        );
    }

}

