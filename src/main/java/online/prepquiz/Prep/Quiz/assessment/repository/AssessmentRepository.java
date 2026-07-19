package online.prepquiz.Prep.Quiz.assessment.repository;

import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment,Long> {
    boolean existsByScopeTypeAndScopeIdAndTitleIgnoreCase(
            AssessmentScopeType scopeType,
            Long scopeId,
            String title
    );
    boolean existsByScopeTypeAndScopeIdAndTitleIgnoreCaseAndIdNot(
            AssessmentScopeType scopeType,
            Long scopeId,
            String title,
            Long id
    );
    List<Assessment> findByScopeTypeAndScopeId(
            AssessmentScopeType scopeType,
            Long scopeId
    );
    List<Assessment> findByStatus(
            AssessmentStatus status
    );
    Page<Assessment> findByScopeTypeAndScopeIdAndStatus(
            AssessmentScopeType scopeType,
            Long scopeId,
            AssessmentStatus status,
            Pageable pageable);
//    List<Assessment> findByScopeTypeAndScopeIdOrderByCreatedAtDesc(
//            AssessmentScopeType scopeType,
//            Long scopeId
//    );
    Page<Assessment> findByScopeTypeAndScopeId(
            AssessmentScopeType scopeType,
            Long scopeId,
            Pageable pageable
    );
    Page<Assessment> findByStatus(
            AssessmentStatus status,
            Pageable pageable
    );
}
