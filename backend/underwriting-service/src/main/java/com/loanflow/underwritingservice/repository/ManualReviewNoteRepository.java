package com.loanflow.underwritingservice.repository;

import com.loanflow.underwritingservice.entities.ManualReviewNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualReviewNoteRepository extends JpaRepository<ManualReviewNote, Long> {
    List<ManualReviewNote> findByUnderwritingCase_UnderwritingCaseIdOrderByCreatedAtAsc(
            Long caseId
    );
}
