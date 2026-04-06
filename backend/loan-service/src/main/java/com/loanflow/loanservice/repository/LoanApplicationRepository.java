package com.loanflow.loanservice.repository;

import com.loanflow.loanservice.entity.LoanApplication;
import com.loanflow.loanservice.entity.LoanApplicationStatus;
import com.loanflow.loanservice.entity.LoanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    Optional<LoanApplication> findByApplicationReference(String reference);

    List<LoanApplication> findByApplicantUserId(Long userId);

    Page<LoanApplication> findAllByStatusAndLoanType(
            LoanApplicationStatus status,
            LoanType loanType,
            Pageable pageable
    );

    boolean existsByApplicantUserIdAndLoanTypeAndStatusIn(
            Long userId,
            LoanType loanType,
            List<LoanApplicationStatus> statuses
    );
}