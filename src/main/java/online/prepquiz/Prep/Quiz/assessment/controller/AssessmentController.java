package online.prepquiz.Prep.Quiz.assessment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.assessment.service.AssessmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    //Create Assessment
    @PostMapping
    public ResponseEntity<AssessmentResponseDto> createAssessment(
            @Valid @RequestBody CreateAssessmentDto request
    ) {
        AssessmentResponseDto response =
                assessmentService.createAssessment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    //Get Assessment
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponseDto> getAssessmentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                assessmentService.getAssessmentById(id)
        );
    }

    //List Assessments
    @GetMapping
    public ResponseEntity<Page<AssessmentResponseDto>> getAssessments(
            @RequestParam(required = false)
            AssessmentScopeType scopeType,

            @RequestParam(required = false)
            Long scopeId,

            @RequestParam(required = false)
            AssessmentStatus status,

            Pageable pageable
    ) {

        return ResponseEntity.ok(
                assessmentService.getAssessments(
                        scopeType,
                        scopeId,
                        status,
                        pageable
                )
        );
    }


    //Update Assessment
    @PutMapping("/{id}")
    public ResponseEntity<AssessmentResponseDto> updateAssessment(
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateAssessmentDto request
    ) {

        return ResponseEntity.ok(
                assessmentService.updateAssessment(
                        id,
                        request
                )
        );
    }

    //Delete Assessment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(
            @PathVariable Long id
    ) {

        assessmentService.deleteAssessment(id);

        return ResponseEntity.noContent().build();
    }
    //Publish Assessment
    @PostMapping("/{id}/publish")
    public ResponseEntity<Void> publishAssessment(
            @PathVariable Long id
    ) {

        assessmentService.publishAssessment(id);

        return ResponseEntity.noContent().build();
    }


    //Archive Assessment
    @PostMapping("/{id}/archive")
    public ResponseEntity<Void> archiveAssessment(
            @PathVariable Long id
    ) {

        assessmentService.archiveAssessment(id);

        return ResponseEntity.noContent().build();
    }


}
