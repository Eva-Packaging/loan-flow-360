package com.loanflow.document_service.repository;

import com.loanflow.document_service.entity.enums.DocumentVerificationAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentVerificationAuditRepository extends JpaRepository<DocumentVerificationAudit, Long> {

    List<DocumentVerificationAudit>findByDocument_DocumentIdOrderByVerifiedAtAsc(
            Long documentId);

    Optional<DocumentVerificationAudit> findTopByDocument_DocumentIdOrderByVerifiedAtDesc(
            Long documentId);
}
