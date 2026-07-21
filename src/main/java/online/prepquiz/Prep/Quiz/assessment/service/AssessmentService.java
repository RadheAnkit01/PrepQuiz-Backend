package online.prepquiz.Prep.Quiz.assessment.service;

import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.common.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssessmentService {

    AssessmentResponseDto createAssessment(
            CreateAssessmentDto request
    );

    AssessmentResponseDto getAssessmentById(
            Long id
    );

    PageResponse<AssessmentResponseDto> getAssessments(
            AssessmentScopeType scopeType,
            Long scopeId,
            AssessmentStatus status,
            int page, int pageSize, String direction, String sortBy
    );

    AssessmentResponseDto updateAssessment(
            Long id,
            UpdateAssessmentDto request
    );

    void deleteAssessment(Long id);

    void publishAssessment(Long id);

    void archiveAssessment(Long id);
}