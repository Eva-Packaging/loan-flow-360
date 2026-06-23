package com.loanflow.document_service.repository;
import com.loanflow.document_service.entity.enums.ApplicationDocument;
import com.loanflow.document_service.entity.enums.DocumentType;
import com.loanflow.document_service.entity.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApplicationDocumentRepository extends JpaRepository<ApplicationDocument, Long> {
    List<ApplicationDocument> findByApplicationId(
            Long applicationId);

    Optional<ApplicationDocument> findByApplicationIdAndDocumentType(
            Long applicationId,
            DocumentType documentType);

    List<ApplicationDocument> findByApplicationIdAndVerificationStatus(
            Long applicationId,
            VerificationStatus status);

    long countByApplicationIdAndVerificationStatus(
            Long applicationId,
            VerificationStatus status);

    boolean existsByStorageKey(String storageKey);
}
