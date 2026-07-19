package online.prepquiz.Prep.Quiz.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class QuestionResponseDto {
    private Long id;

    private Long chapterId;

    private String text;

    private QuestionType questionType;

    private Difficulty difficulty;

    private String explanation;

    private Boolean pyq;

    private QuestionStatus status;

    private List<OptionResponseDto> options;
}
