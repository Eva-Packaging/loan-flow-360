package com.loanflow.loanservice.services;

import com.loanflow.common.dto.application.response.ApplicationDetailResponse;

public interface LoanApplicationService {
    ApplicationDetailResponse getApplicationById(Long applicationId, Long callerUserId, String callerRole);

}
