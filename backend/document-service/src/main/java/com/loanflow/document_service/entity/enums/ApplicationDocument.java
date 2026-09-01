package com.loanflow.document_service.entity.enums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import static jakarta.persistence.CascadeType.ALL;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "application_documents",
        uniqueConstraints = { @UniqueConstraint(
                name = " uk_application_documents_storage_key",
                columnNames = "storage_key"
            ),
                @UniqueConstraint(
                        name = " uk_application_documents_app_doc_type",
                        columnNames = {"application_id", "document_type"}
                )
        }
    )

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApplicationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "application_id", nullable = false)
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 50)
    private DocumentType documentType;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "storage_key", length = 500)
    private String storageKey;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "file_size_bytes")
    private Long fileSizeBytes;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_status", nullable = false, length = 20)
    private UploadStatus uploadStatus;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "verification_status",
            nullable = false,
            length = 30,
            columnDefinition = "VARCHAR(30) DEFAULT 'PENDING_REVIEW'"
    )
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING_REVIEW;

    @Column(name = "uploaded_by_user_id")
    private Long uploadedByUserId;

    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = ALL)
    private String auditRecords;

}
