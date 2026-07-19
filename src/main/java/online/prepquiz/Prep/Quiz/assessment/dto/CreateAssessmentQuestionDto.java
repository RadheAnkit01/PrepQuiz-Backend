package online.prepquiz.Prep.Quiz.assessment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssessmentQuestionDto implements AssessmentQuestionRequest{

    @NotNull
    private Long questionId;

    @NotNull
    @Positive
    private Integer displayOrder;

    @NotNull
    @Positive
    private Integer marks;

    @NotNull
    @PositiveOrZero
    private Integer negativeMarks;
}