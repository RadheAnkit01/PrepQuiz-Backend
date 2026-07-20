package online.prepquiz.Prep.Quiz.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class CreateQuestionDto {
    @NotNull
    private Long chapterId;

    @NotBlank
    @Size(min = 5, max = 1000)
    private String text;

    @NotNull
    private QuestionType questionType;

    @NotNull
    private Difficulty difficulty;

    @Size(max = 2000)
    private String explanation;

    @NotNull
    private Boolean pyq;

    @NotEmpty
    @Size(min = 2, max = 6)
    private List<CreateOptionDto> options;
}
