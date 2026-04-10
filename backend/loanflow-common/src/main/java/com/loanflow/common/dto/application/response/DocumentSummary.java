package com.loanflow.common.dto.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSummary {
    private int required;
    private int uploaded;
    private int verfied;
}
