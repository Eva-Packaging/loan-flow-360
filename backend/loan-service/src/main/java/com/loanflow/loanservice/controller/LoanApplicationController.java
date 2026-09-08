package com.loanflow.loanservice.controller;

import com.loanflow.common.dto.application.response.ApplicationDetailResponse;
import com.loanflow.common.dto.application.response.*;
import com.loanflow.common.dto.application.request.CreateLoanApplicationRequest;
import com.loanflow.common.dto.application.response.CreateLoanApplicationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final com.loanflow.loanservice.service.LoanApplicationService createLoanApplicationService;
    private final com.loanflow.loanservice.services.LoanApplicationService getLoanApplicationService;

    @PostMapping
    public ResponseEntity<CreateLoanApplicationResponse> createApplication(
            @RequestBody @Valid CreateLoanApplicationRequest request)
             {
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createLoanApplicationService.createApplication(request));
             }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationDetailResponse> getApplicationById(
        @PathVariable Long applicationId,
        @AuthenticationPrincipal Jwt jwt) {

        Long callerUserId = jwt.getClaim("userId");
        String callerRole = jwt.<List<String>>getClaim("roles").get(0);

        return ResponseEntity.ok(getLoanApplicationService.getApplicationById(applicationId, callerUserId, callerRole));
    }
}
