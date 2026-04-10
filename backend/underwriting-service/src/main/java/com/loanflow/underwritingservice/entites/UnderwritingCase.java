package com.loanflow.underwritingservice.entites;


import com.loanflow.underwritingservice.entites.enums.CaseStatus;
import com.loanflow.underwritingservice.entites.enums.DecisionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "underwriting_cases")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UnderwritingCases {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private long applicationId;

    @Enumerated(EnumType.STRING)
    private CaseStatus caseStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DecisionStatus decisionStatus;

    @Column(length = 500, nullable =false)
    private String decisionReason;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean manualReviewRequired = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "underwritingCase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UnderwritingCases> checks;

    @OneToMany(mappedBy = "underwritingCase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UnderwritingChecks> decisions;

    //@OneToMany(mappedBy = "underwritingCase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<ReviewNote> reviewNotes;


}
