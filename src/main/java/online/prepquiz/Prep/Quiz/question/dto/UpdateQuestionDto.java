package online.prepquiz.Prep.Quiz.question.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;

import java.util.List;

@Data
public class UpdateQuestionDto {

    @NotBlank(message = "Question text is required.")
    private String text;

    @NotNull(message = "Question type is required.")
    private QuestionType questionType;

    @NotNull(message = "Difficulty is required.")
    private Difficulty difficulty;

    private String explanation;

    @NotNull(message = "PYQ flag is required.")
    private Boolean pyq;

    @NotNull(message = "Question status is required.")
    private QuestionStatus status;

    @Valid
    @NotEmpty(message = "Question must have at least 2 options.")
    @Size(min = 2, max = 6, message = "Question must have between 2 and 6 options.")
    private List<UpdateOptionDto> options;
}