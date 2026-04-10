package repository;

import entites.UnderwritingChecks;
import entites.enums.CheckType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnderwritingCheckRepository extends JpaRepository<UnderwritingChecks, Long> {

    List<UnderwritingChecks> findByUnderwritingCase_underWritingCaseId(Long caseId);
    Optional<UnderwritingChecks> findByUnderwritingCase_UnderwritingCaseIdAndCheckType
            (long caseId, CheckType checkType);

}
