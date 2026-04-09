package com.loanflow.common.dto.request;

import com.loanflow.common.dto.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLoanApplicationRequest {

    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    @NotNull(message = "Requested amount is required")
    @DecimalMin(value = "1000.00", message = "Requested amount must be at least 1000.00")
    @DecimalMax(value = "10000000.00", message = "Requested amount exceeds maximum allowed")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal amount;

    @NotNull(message = "Term is required")
    @Min(value = 6, message = "Term must be at least 6 months")
    @Max(value = 360, message = "Term cannot exceed 360 months")
    private Integer termMonths;

    @NotNull(message = "Annual income is required")
    @DecimalMin(value = "0.01", message = "Annual income must be greater than zero")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal annualIncome;

    @NotNull(message = "Employment status is required")
    private EmploymentStatus employmentStatus;

    @NotNull(message = "Applicant details are required")
    @Valid
    private ApplicantDetailsRequest applicantDetails;
}