package com.loanflow.document_service.entity.enums;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "document_verification_audit")
@Getter
@NoArgsConstructor

public class DocumentVerificationAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_audit_id")
    private Long verificationAuditId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Long documentId;

    @Column(name = "verified_by_user_id", nullable = false)
    private BigInteger verifiedByUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_result", nullable = false)
    private VerificationResult verificationResult;

    @Column(name = "notes", length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "verified_at", nullable = false, updatable = false)
    private Instant verifiedAt;

    @Builder
    DocumentVerificationAudit(
            Long verificationAuditId,
            Long documentId,
            BigInteger verifiedByUserId,
            VerificationResult verificationResult,
            String notes,
            Instant verifiedAt
    ){
        this.verificationAuditId = verificationAuditId;
        this.documentId = documentId;
        this.verifiedByUserId = verifiedByUserId;
        this.verificationResult = verificationResult;
        this.notes = notes;
        this.verifiedAt = verifiedAt;
    }
}
