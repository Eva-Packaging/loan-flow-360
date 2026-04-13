package com.loanflow.loanservice.controller;

import com.loanflow.common.dto.application.request.CreateLoanApplicationRequest;
import com.loanflow.common.dto.application.response.CreateLoanApplicationResponse;
import com.loanflow.loanservice.service.LoanApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    @PostMapping

    public ResponseEntity<CreateLoanApplicationResponse> createApplication(
            @RequestBody @Valid CreateLoanApplicationRequest request)
             {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loanApplicationService.createApplication (request));
             }
}