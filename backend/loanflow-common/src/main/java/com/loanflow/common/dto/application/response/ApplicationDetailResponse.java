package com.loanflow.common.dto.application.response;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationDetailResponse {
    private Long applicationId;
    private LoanStatus status;
    private LoanType loanType;
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal annualIncome;
    private Instant submittedAt;
    private ApplicantDetailsSummary applicantDetails;
    private DocumentSummary documentSummary;
}
