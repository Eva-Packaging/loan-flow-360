package com.loanflow.common.dto.application.response;

import com.loanflow.common.dto.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationStatusResponse {
    private Long applicationId;
    private LoanStatus previousStatus;
    private LoanStatus currentStatus;
    private String reason;
}
