package com.loanflow.loanservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

    @Entity
    @Table(name = "application_status_history")
    @Getter
    @Setter
    public class ApplicationStatusHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "status_history_id")
        private Long statusHistoryId;

        @ManyToOne
        @JoinColumn(name = "application_id", nullable = false, updatable = false)
        private LoanApplication loanApplication;

        @Column(name = "previous_status", length = 50, updatable = false)
        private String previousStatus;

        @Column(name = "new_status", nullable = false, length = 50, updatable = false)
        private String newStatus;

        @Column(name = "changed_by_user_id", nullable = false, updatable = false)
        private Long changedByUserId;

        @Column(name = "change_reason", length = 255, updatable = false)
        private String changeReason;

        @CreationTimestamp
        @Column(name = "changed_at", nullable = false, updatable = false)
        private LocalDateTime changedAt;
    }

