package com.loanflow.common.event.loan;

import com.loanflow.common.event.KafkaTopics;
import com.loanflow.common.event.LoanFlowEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@SuperBuilder
@NoArgsConstructor
public class LoanStatusUpdatedEvent extends LoanFlowEvent {
    Long applicationId;
    String applicationReference;
    String previousStatus;
    String newStatus;
    Long changedByUserId;
    Instant changedAt;

    public static LoanStatusUpdatedEventBuilder<?, ?> builder() {
        return new LoanStatusUpdatedEventBuilderImpl()
                .eventType(KafkaTopics.LOAN_STATUS_UPDATED);
    }
}
