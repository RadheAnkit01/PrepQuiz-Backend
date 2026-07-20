package online.prepquiz.Prep.Quiz.question.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.QuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateQuestionDto;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionScopeType;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;
import online.prepquiz.Prep.Quiz.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @Valid @RequestBody CreateQuestionDto request
    ) {

        QuestionResponseDto response =
                questionService.createQuestion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                questionService.getQuestionById(id)
        );
    }

//    @GetMapping("/chapter/{chapterId}")
//    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByChapter(
//            @PathVariable Long chapterId
//    ) {
//
//        return ResponseEntity.ok(
//                questionService.getQuestionsByChapter(chapterId)
//        );
//    }


    @GetMapping
    public ResponseEntity<Page<QuestionResponseDto>> getQuestions(
            @RequestParam(required = false) QuestionScopeType scopeType,
            @RequestParam(required = false) Long scopeId,
            @RequestParam(required = false) QuestionType questionType,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) QuestionStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50")
            @Min(value = 1, message = "pageSize must be at least 1")
            @Max(value = 100, message = "pageSize cannot be greater than 50")
            int pageSize,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "id") String sortBy
    ) {

        return ResponseEntity.ok(
                questionService.getQuestions(
                        scopeType,
                        scopeId,
                        questionType,
                        difficulty,
                        status,
                        page,
                        pageSize,
                        direction,
                        sortBy
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionDto request
    ) {

        return ResponseEntity.ok(
                questionService.updateQuestion(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id
    ) {

        questionService.deleteQuestion(id);

        return ResponseEntity.noContent().build();
    }
}