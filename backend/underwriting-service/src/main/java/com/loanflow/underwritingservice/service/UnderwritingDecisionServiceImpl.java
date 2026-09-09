package com.loanflow.underwritingservice.service;

import com.loanflow.underwritingservice.dto.UnderwritingDecisionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class UnderwritingDecisionServiceImpl implements UnderwritingDecisionService {
    @Override
    public UnderwritingDecisionResponse getDecision(Long applicationId) {
        // TODO: Replace with actual implementation when real persistence and decision-making logic is available
        return UnderwritingDecisionResponse.builder()
                .applicationId(applicationId)
                .decisionStatus("APPROVED")
                .decisionAt(Instant.parse("2026-03-24T16:02:11Z"))
                .decisionReason("Income and debt ratio within policy thresholds")
                .recommendedAmount(new BigDecimal("25000.00"))
                .recommendedTermMonths(48)
                .manualReviewRequired(false)
                .build();
    }
}
