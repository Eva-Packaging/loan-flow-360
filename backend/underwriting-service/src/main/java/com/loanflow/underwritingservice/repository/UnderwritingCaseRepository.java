package com.loanflow.underwritingservice.repository;

import com.loanflow.underwritingservice.entities.UnderwritingCase;
import com.loanflow.underwritingservice.entities.enums.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCase, Long> {
    Optional<UnderwritingCase> findByApplicationId (Long applicationId);
    boolean existsByApplicationId (Long applicationId);
    List<UnderwritingCase> findByCaseStatus (CaseStatus caseStatus);

}