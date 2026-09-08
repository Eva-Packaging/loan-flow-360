package com.loanflow.common.event.underwriting;

import com.loanflow.common.event.KafkaTopics;
import com.loanflow.common.event.LoanFlowEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@NoArgsConstructor
public class LoanUnderwritingCompletedEvent extends LoanFlowEvent {
    Long applicationId;
    Long underwritingCaseId;
    String decisionStatus;
    String decisionReason;
    Instant completedAt;

    public static LoanUnderwritingCompletedEventBuilder<?, ?> builder() {
        return new LoanUnderwritingCompletedEventBuilderImpl()
                .eventType(KafkaTopics.LOAN_UNDERWRITING_COMPLETED);
    }
}
