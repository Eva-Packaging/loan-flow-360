package com.loanflow.loanservice.services;

import com.loanflow.common.dto.application.response.ApplicantDetailsSummary;
import com.loanflow.common.dto.application.response.ApplicationDetailResponse;
import com.loanflow.common.dto.application.response.DocumentSummary;
import com.loanflow.common.dto.enums.LoanStatus;
import com.loanflow.common.dto.enums.LoanType;
import com.loanflow.common.exception.AccessDeniedException;
import com.loanflow.common.exception.ResourceNotFoundException;
import com.loanflow.loanservice.entity.LoanApplication;
import com.loanflow.loanservice.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApplicationServiceImpl(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    @Override
    public ApplicationDetailResponse getApplicationById(Long applicationId, Long callerUserId, String callerRole) {
        LoanApplication application = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "LoanApplication not found with id: " + applicationId));
        if ("APPLICANT".equals(callerRole)
                && !application.getApplicantUserId().equals(callerUserId)) {
            throw new AccessDeniedException(
                    "Access denied to application: " + applicationId);
        }

        return ApplicationDetailResponse.builder()
                .applicationId(application.getApplicationId())
                .status(LoanStatus.valueOf(application.getStatus().name()))
                .loanType(LoanType.valueOf(application.getLoanType().name()))
                .amount(application.getRequestedAmount())
                .termMonths(application.getTermMonths())
                .annualIncome(application.getAnnualIncome())
                .submittedAt(application.getSubmittedAt().toInstant(ZoneOffset.UTC))
                .applicantDetails(ApplicantDetailsSummary.builder()
                        .firstName("")
                        .lastName("")
                        .email("")
                        .build())
                .documentSummary(DocumentSummary.builder()
                        .required(0)
                        .uploaded(0)
                        .verified(0)
                        .build())
                .build();
    }
}
