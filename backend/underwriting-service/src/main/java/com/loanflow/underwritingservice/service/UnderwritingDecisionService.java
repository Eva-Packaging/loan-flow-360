package com.loanflow.underwritingservice.service;

import com.loanflow.underwritingservice.dto.UnderwritingDecisionResponse;

public interface UnderwritingDecisionService {
    UnderwritingDecisionResponse getDecision(Long applicationId);
}
