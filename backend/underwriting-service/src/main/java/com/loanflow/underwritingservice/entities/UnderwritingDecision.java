package com.loanflow.underwritingservice.entities;

import com.loanflow.underwritingservice.entities.enums.DecisionType;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long decisionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriting_case_id")
    private UnderwritingCase underwritingCase;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DecisionType decisionType;

    @Column(precision = 15, scale = 2)
    private BigDecimal recommendedAmount;

    private Long decidedByUserId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime decisionAt;

}
