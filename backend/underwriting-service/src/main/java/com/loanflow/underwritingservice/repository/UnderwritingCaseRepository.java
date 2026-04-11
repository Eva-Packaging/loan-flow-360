package com.loanflow.underwritingservice.repository;

import com.loanflow.underwritingservice.entities.UnderwritingCase;
import com.loanflow.underwritingservice.entities.enums.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCase, Long> {
    Optional<UnderwritingCase> findByapplicationId (Long appllicationId);
    boolean existByApplicationId (Long applicationId);
    List<UnderwritingCase> findByCaseStatus (CaseStatus caseStatus);

}