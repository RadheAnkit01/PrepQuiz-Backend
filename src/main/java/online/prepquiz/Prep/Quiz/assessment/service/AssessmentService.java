package online.prepquiz.Prep.Quiz.assessment.service;

import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssessmentService {

    AssessmentResponseDto createAssessment(
            CreateAssessmentDto request
    );

    AssessmentResponseDto getAssessmentById(
            Long id
    );

    Page<AssessmentResponseDto> getAssessments(
            AssessmentScopeType scopeType,
            Long scopeId,
            AssessmentStatus status,
            Pageable pageable
    );

    AssessmentResponseDto updateAssessment(
            Long id,
            UpdateAssessmentDto request
    );

    void deleteAssessment(Long id);

    void publishAssessment(Long id);

    void archiveAssessment(Long id);
}