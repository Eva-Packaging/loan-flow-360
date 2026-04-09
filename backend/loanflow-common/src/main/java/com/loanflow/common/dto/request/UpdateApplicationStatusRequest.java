package com.loanflow.common.dto.request;

import com.loanflow.common.dto.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApplicationStatusRequest {

    @NotNull(message = "Status is required")
    private LoanStatus status;

    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;
}
