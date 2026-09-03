package com.loanflow.underwritingservice.controller;

import com.loanflow.underwritingservice.dto.UnderwritingDecisionResponse;
import com.loanflow.underwritingservice.service.UnderwritingDecisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/underwriting")
public class UnderwritingController {
    private final UnderwritingDecisionService underwritingDecisionService;

    /**
     * Endpoint to retrieve the underwriting decision for a given application ID.
     * Auth requirements:
     * ADMIN and LOAN_OFFICER roles get full access to all applications.
     * Applicants can only get summaries for their own applications.
     * (Enforced by API Gateway)
     * @param applicationId The ID of the loan application for which the underwriting decision is requested.
     * @return The underwriting decision response.
     */
    @GetMapping("/{applicationId}")
    public ResponseEntity<UnderwritingDecisionResponse> getUnderwritingDecision(@PathVariable Long applicationId) {
        UnderwritingDecisionResponse decisionResponse = underwritingDecisionService.getDecision(applicationId);
        return ResponseEntity.ok(decisionResponse);
    }
}
