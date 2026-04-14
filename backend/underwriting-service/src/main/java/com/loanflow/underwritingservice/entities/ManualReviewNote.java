package com.loanflow.underwritingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "manual_review_notes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ManualReviewNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underwriting_case_id")
    private UnderwritingCase underwritingCase;


    private Long reviewerUserId;

    @Column(length = 1000)
    private String noteText;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;




}
