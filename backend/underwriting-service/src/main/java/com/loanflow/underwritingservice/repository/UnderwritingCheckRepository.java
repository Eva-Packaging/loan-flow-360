package com.loanflow.underwritingservice.repository;

import com.loanflow.underwritingservice.entities.UnderwritingChecks;
import com.loanflow.underwritingservice.entities.enums.CheckType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnderwritingCheckRepository extends JpaRepository<UnderwritingChecks, Long> {

    List<UnderwritingChecks> findByUnderwritingCase_underWritingCaseId(Long caseId);
    Optional<UnderwritingChecks> findByUnderwritingCase_UnderwritingCaseIdAndCheckType
            (long caseId, CheckType checkType);

}
