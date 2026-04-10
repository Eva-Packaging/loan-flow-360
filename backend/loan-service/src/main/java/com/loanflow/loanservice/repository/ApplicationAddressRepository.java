package com.loanflow.loanservice.repository;

import com.loanflow.loanservice.entity.ApplicationAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

    public interface ApplicationAddressRepository extends JpaRepository<ApplicationAddress, Long> {

        List<ApplicationAddress> findByLoanApplication_ApplicationId(Long applicationId);
    }

