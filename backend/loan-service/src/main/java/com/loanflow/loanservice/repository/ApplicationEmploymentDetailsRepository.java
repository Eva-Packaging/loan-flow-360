package com.loanflow.loanservice.repository;

import com.loanflow.loanservice.entity.ApplicationEmploymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface ApplicationEmploymentDetailsRepository
            extends JpaRepository<ApplicationEmploymentDetails, Long> {

        Optional<ApplicationEmploymentDetails> findByLoanApplication_ApplicationId(Long applicationId);
    }

