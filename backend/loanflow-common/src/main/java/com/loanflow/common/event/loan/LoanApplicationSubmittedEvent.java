package com.loanflow.common.event.loan;

import com.loanflow.common.event.LoanFlowEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@SuperBuilder
@NoArgsConstructor
public class LoanApplicationSubmittedEvent extends LoanFlowEvent {
    Long applicationId;
    String applicationReference;
    Long applicantUserId;
    String loanType;
    BigDecimal requestedAmount;
    Integer termMonths;
    Instant submittedAt;

    public static LoanApplicationSubmittedEventBuilder<?, ?> builder() {
        return new LoanApplicationSubmittedEventBuilderImpl()
                .eventType("loan.application.submitted");
    }
}
