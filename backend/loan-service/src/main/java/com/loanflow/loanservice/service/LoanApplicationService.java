package com.loanflow.loanservice.service;

import com.loanflow.common.dto.application.request.CreateLoanApplicationRequest;
import com.loanflow.common.dto.application.response.CreateLoanApplicationResponse;

public interface LoanApplicationService {

    CreateLoanApplicationResponse createApplication(
            CreateLoanApplicationRequest request
    );
}