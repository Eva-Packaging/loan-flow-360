package com.loanflow.loanservice.policy;

import com.loanflow.loanservice.entity.LoanApplicationStatus;
import com.loanflow.loanservice.exception.IllegalStatusTransitionException;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class LoanStatusTransitionPolicy {

    private final Map<LoanApplicationStatus, Set<LoanApplicationStatus>>
            allowedTransitions =
            new EnumMap<>(LoanApplicationStatus.class);

    public LoanStatusTransitionPolicy() {

        allowedTransitions.put(
                LoanApplicationStatus.SUBMITTED,
                EnumSet.of(LoanApplicationStatus.UNDER_REVIEW)
        );

        allowedTransitions.put(
                LoanApplicationStatus.UNDER_REVIEW,
                EnumSet.of(
                        LoanApplicationStatus.APPROVED,
                        LoanApplicationStatus.REJECTED
                )
        );

        allowedTransitions.put(
                LoanApplicationStatus.REJECTED,
                EnumSet.noneOf(LoanApplicationStatus.class)
        );


    }

    public boolean isAllowed(
            LoanApplicationStatus from,
            LoanApplicationStatus to) {

        if (from == null || to == null) {
            throw new IllegalArgumentException(
                    "from and to statuses cannot be null");
        }
        return allowedTransitions
                .getOrDefault(from, Set.of())
                .contains(to);
    }

    public void validateTransition(
            LoanApplicationStatus from,
            LoanApplicationStatus to) {

        if (!isAllowed(from, to)) {
            throw new IllegalStatusTransitionException(from, to);
        }
    }

}
