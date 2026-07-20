package online.prepquiz.Prep.Quiz.question.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import online.prepquiz.Prep.Quiz.question.dto.BulkQuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.QuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateQuestionDto;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionScopeType;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {

    QuestionResponseDto createQuestion(CreateQuestionDto request);

    BulkQuestionResponseDto createQuestions(
            List<CreateQuestionDto> requests
    );

    QuestionResponseDto getQuestionById(Long id);

//    List<QuestionResponseDto> getQuestionsByChapter(Long chapterId);

    QuestionResponseDto updateQuestion(Long id, UpdateQuestionDto request);

//    void inactiveQuestion(Long id);

    void deleteQuestion(Long id);

    Page<QuestionResponseDto> getQuestions(QuestionScopeType scopeType, Long scopeId, QuestionType questionType, Difficulty difficulty, QuestionStatus status, int page, @Min(value = 1, message = "pageSize must be at least 1") @Max(value = 50, message = "pageSize cannot be greater than 50") int pageSize, String direction, String sortBy);
}