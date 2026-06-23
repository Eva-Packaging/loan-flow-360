package com.loanflow.document_service.repository;

import com.loanflow.document_service.entity.enums.DocumentRequirement;
import com.loanflow.document_service.entity.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRequirementRepository extends JpaRepository<DocumentRequirement,Long> {

    List<DocumentRequirement>findByLoanTypeAndActiveFlagTrueOrderByDisplayOrderAsc(
            String loanType);

    Optional<DocumentRequirement>findByLoanTypeAndDocumentTypeAndActiveFlagTrue(
            String loanType,
            DocumentType documentType);
}
