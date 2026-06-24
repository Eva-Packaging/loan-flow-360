package com.loanflow.document_service.entity.enums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "document_verification_audit")
@Getter
@NoArgsConstructor


public class DocumentRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long requirementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "loanType", nullable = false)
    private String loanType;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentType", nullable = false, length = 50)
    private String documentType;

    @Column(name ="requiredFlag" , nullable = false)
    private Boolean requiredFlag;

    @Column(name = "display_Order", nullable = false)
    private int displayOrder;

    @Column(name = "activeFlag", nullable = false)
    private Boolean activeFlag;

    @Builder
    DocumentRequirement(
            Long requirementId,
            String loanType,
            DocumentType documentType,
            boolean requiredFlag,
            int displayOrder,
            boolean activeFlag
    ){
        this.requirementId = requirementId;
        this.loanType = loanType;
        this.documentType = documentType.name();
        this.requiredFlag = requiredFlag;
        this.displayOrder = displayOrder;
        this.activeFlag = activeFlag;
    }
}
