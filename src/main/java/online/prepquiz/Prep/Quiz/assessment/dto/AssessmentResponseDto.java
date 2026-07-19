package online.prepquiz.Prep.Quiz.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentType;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentResponseDto {

    private Long id;

    private String title;

    private String description;

    private AssessmentType assessmentType;

    private AssessmentScopeType scopeType;

    private Long scopeId;

    private Integer durationMinutes;

    private Integer passingMarks;

    private Boolean shuffleQuestions;

    private Boolean shuffleOptions;

    private Boolean showAnswersAfterSubmit;

    private Integer version;

    private Integer totalMarks;

    private AssessmentStatus status;

    private List<AssessmentQuestionResponseDto> assessmentQuestions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}