package com.loanflow.common.dto.application.response;

import com.loanflow.common.dto.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CreateLoanApplicationResponse {
    private Long applicationId;
    private LoanStatus status;
    private Instant submittedAt;
    private LoanType loanType;
    private BigDecimal amount;
    private String nextStep;
}
