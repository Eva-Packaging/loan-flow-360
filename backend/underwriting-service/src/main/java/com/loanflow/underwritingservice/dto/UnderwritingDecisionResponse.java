package com.loanflow.underwritingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnderwritingDecisionResponse {
    private long applicationId;
    private String decisionStatus;
    private Instant decisionAt;
    private String decisionReason;
    private BigDecimal recommendedAmount;
    private Integer recommendedTermMonths;
    private boolean manualReviewRequired;
}
