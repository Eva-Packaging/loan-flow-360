package com.loanflow.underwritingservice.entities;

import com.loanflow.underwritingservice.entities.enums.CheckStatus;
import com.loanflow.underwritingservice.entities.enums.CheckType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "underwriting_checks")
@Getter
@Setter
@NoArgsConstructor

public class UnderwritingChecks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_id")
    private Long checkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriting_case_id")
    private UnderwritingCase underwritingCase;

    @Enumerated(EnumType.STRING)
    @Column(name = "check_type", nullable = false)
    private CheckType checkType;

    @Enumerated(EnumType.STRING)
    @Column(name = "check_status", nullable = false)
    private CheckStatus checkStatus;

    @Column(name = "result_code", length = 50)
    private String resultCode;

    @Column(name = "result_summary", length = 255)
    private String resultSummary;

    @CreationTimestamp
    @Column(name = "executed_at", nullable = false, updatable = false)
    private LocalDateTime executedAt;




}
