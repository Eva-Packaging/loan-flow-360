package com.loanflow.underwritingservice.entities;


import com.loanflow.underwritingservice.entities.enums.CaseStatus;
import com.loanflow.underwritingservice.entities.enums.DecisionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "underwriting_cases")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UnderwritingCase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "underwriting_case_id")
    private Long id;

    @Column(name = "application_id", unique = true, nullable = false)
    private long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_status", nullable = false)
    private CaseStatus caseStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_status")
    private DecisionStatus decisionStatus;

    @Column(name = "decision_reason", length = 500)
    private String decisionReason;

    @Column(name = "manual_review_required", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean manualReviewRequired = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    // @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<UnderwritingCase> checks;

    //@OneToMany(mappedBy = "underwritingCase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<UnderwritingDecisions> decisions;

    //@OneToMany(mappedBy = "underwritingCase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<ReviewNote> reviewNotes;


}
