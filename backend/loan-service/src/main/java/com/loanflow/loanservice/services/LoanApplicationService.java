package com.loanflow.loanservice.services;

import com.loanflow.common.dto.application.request.CreateLoanApplicationRequest;
import com.loanflow.common.dto.application.response.ApplicationDetailResponse;
import com.loanflow.common.dto.application.response.CreateLoanApplicationResponse;
import com.loanflow.common.dto.application.request.UpdateApplicationStatusRequest;

public interface LoanApplicationService {
    ApplicationDetailResponse getApplicationById(Long applicationId, Long callerUserId, String callerRole);

    CreateLoanApplicationResponse createApplication(
            CreateLoanApplicationRequest request



    );

    void updateApplicationStatus(
            Long applicationId,
            UpdateApplicationStatusRequest request
    );
}


