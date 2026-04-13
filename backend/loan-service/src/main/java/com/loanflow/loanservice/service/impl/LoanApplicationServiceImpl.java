package com.loanflow.loanservice.service.impl;

import com.loanflow.common.dto.application.request.CreateLoanApplicationRequest;
import com.loanflow.common.dto.application.response.CreateLoanApplicationResponse;
import com.loanflow.common.dto.enums.LoanStatus;
import com.loanflow.loanservice.service.LoanApplicationService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    @Override
    public CreateLoanApplicationResponse createApplication(
            CreateLoanApplicationRequest request) {

        return CreateLoanApplicationResponse.builder()
                .applicationId(1L)
                .status(LoanStatus.SUBMITTED)
                .submittedAt(Instant.now())
                .loanType(request.getLoanType())
                .amount(request.getAmount())
                .nextStep("Application submitted successfully")
                .build();
    }
}