package repository;

import entites.UnderwritingCases;
import entites.enums.CaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnderwritingCaseRepository extends JpaRepository<UnderwritingCases, Long> {
    Optional<UnderwritingCases> findByapplicationId (Long appllicationId);
    boolean existByApplicationId (Long applicationId);
    List<UnderwritingCases> findByCaseStatus (CaseStatus caseStatus);

}