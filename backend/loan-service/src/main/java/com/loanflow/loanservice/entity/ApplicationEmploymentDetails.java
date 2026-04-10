package com.loanflow.loanservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

    @Entity
    @Table(name = "application_employment_details")
    @Getter
    @Setter
    public class ApplicationEmploymentDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employment_detail_id")
        private Long employmentDetailId;

        @OneToOne
        @JoinColumn(name = "application_id", nullable = false, unique = true)
        private LoanApplication loanApplication;

        @Column(name = "employer_name", nullable = false, length = 255)
        private String employerName;

        @Column(name = "job_title", nullable = false, length = 150)
        private String jobTitle;

        @Column(name = "employment_type", nullable = false, length = 50)
        private String employmentType;

        @Column(name = "monthly_income", nullable = false, precision = 15, scale = 2)
        private BigDecimal monthlyIncome;

        @Column(name = "years_employed", nullable = false, precision = 4, scale = 1)
        private BigDecimal yearsEmployed;
    }

