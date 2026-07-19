package online.prepquiz.Prep.Quiz.assessment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentType;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAssessmentDto {

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

    private Boolean shuffleQuestions;

    private Boolean shuffleOptions;

    private Boolean showAnswersAfterSubmit;

    @NotEmpty
    @Valid
    private List<UpdateAssessmentQuestionDto> assessmentQuestions;
}