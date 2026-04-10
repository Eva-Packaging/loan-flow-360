package com.loanflow.loanservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

    @Entity
    @Table(name = "loan_application_addresses")
    @Getter
    @Setter
    public class ApplicationAddress {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "address_id")
        private Long addressId;

        @ManyToOne
        @JoinColumn(name = "application_id", nullable = false)
        private LoanApplication loanApplication;

        @Column(name = "address_type", nullable = false, length = 30)
        private String addressType;

        @Column(name = "line1", nullable = false, length = 255)
        private String line1;

        @Column(name = "line2", length = 255)
        private String line2;

        @Column(name = "city", nullable = false, length = 100)
        private String city;

        @Column(name = "state_code", nullable = false, length = 20)
        private String stateCode;

        @Column(name = "postal_code", nullable = false, length = 20)
        private String postalCode;

        @Column(name = "country_code", nullable = false, length = 10)
        private String countryCode = "CA";
    }

