package com.loanflow.underwritingservice.entities;

import com.loanflow.underwritingservice.entities.enums.DecisionType;
import com.loanflow.underwritingservice.entities.enums.DecisionSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "underwriting_decisions")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class UnderwritingDecision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "decision_id")
    private Long decisionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriting_case_id")
    private UnderwritingCase underwritingCase;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_type", nullable = false)
    private DecisionType decisionType;

    @Column(name = "recommended_amount", precision = 15, scale = 2)
    private BigDecimal recommendedAmount;

    @Column(name = "recommended_term_months")
    private Integer recommendedTermMonths;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_source", nullable = false)
    private DecisionSource decisionSource;

    @Column(name = "decided_by_user_id")
    private Long decidedByUserId;

    @CreationTimestamp
    @Column(name = "decision_at", nullable = false, updatable = false)
    private LocalDateTime decisionAt;

}
