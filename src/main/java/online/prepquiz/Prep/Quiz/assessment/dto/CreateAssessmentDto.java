package online.prepquiz.Prep.Quiz.assessment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentType;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAssessmentDto {

    @NotBlank
    @Size(max = 200)
    private String title;

    private String description;

    @NotNull
    private AssessmentType assessmentType;

    @NotNull
    private AssessmentScopeType scopeType;

    @NotNull
    private Long scopeId;

    @NotNull
    @Positive
    private Integer durationMinutes;

    @NotNull
    @PositiveOrZero
    private Integer passingMarks;

    @Builder.Default
    private Boolean shuffleQuestions = false;

    @Builder.Default
    private Boolean shuffleOptions = false;

    @Builder.Default
    private Boolean showAnswersAfterSubmit = false;

    @NotEmpty
    @Valid
    private List<CreateAssessmentQuestionDto> assessmentQuestions;
}