package com.loanflow.common.event.document;

import com.loanflow.common.event.LoanFlowEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
public class LoanDocumentVerifiedEvent extends LoanFlowEvent {
    Long applicationId;
    String applicationReference;
    List<String> verifiedDocumentTypes;
    Instant verifiedAt;

    public static LoanDocumentVerifiedEventBuilder<?, ?> builder() {
        return new LoanDocumentVerifiedEventBuilderImpl()
                .eventType("loan.document.verified");
    }
}
