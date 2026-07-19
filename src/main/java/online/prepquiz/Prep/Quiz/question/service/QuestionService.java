package online.prepquiz.Prep.Quiz.question.service;

import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.QuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateQuestionDto;

import java.util.List;

public interface QuestionService {

    QuestionResponseDto createQuestion(CreateQuestionDto request);

    QuestionResponseDto getQuestionById(Long id);

    List<QuestionResponseDto> getQuestionsByChapter(Long chapterId);

    QuestionResponseDto updateQuestion(Long id, UpdateQuestionDto request);

//    void inactiveQuestion(Long id);

    void deleteQuestion(Long id);
}