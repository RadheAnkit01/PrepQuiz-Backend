package online.prepquiz.Prep.Quiz.question.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.QuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateQuestionDto;
import online.prepquiz.Prep.Quiz.question.service.QuestionService;
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

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByChapter(
            @PathVariable Long chapterId
    ) {

        return ResponseEntity.ok(
                questionService.getQuestionsByChapter(chapterId)
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