package com.loanflow.common.dto.identity;

import lombok. AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class UserSummaryDto {
    private Long id;
    private String username;
    private String role;
}