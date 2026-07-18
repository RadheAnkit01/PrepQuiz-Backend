package online.prepquiz.Prep.Quiz.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;
    @GetMapping
    ResponseEntity<List<SubjectDto>> getAllSubjects(){
        return  ResponseEntity.status(HttpStatus.OK).body(subjectService.getAllSubjects());
    }
    @GetMapping(params = "courseId")
    ResponseEntity<List<SubjectDto>> getAllSubjectForCourseId(@RequestParam Long courseId){
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getAllSubjectForCourseId(courseId));
    }

    @PostMapping
    ResponseEntity<SubjectDto> createSubject(@RequestBody CreateSubjectDto createSubjectDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.createSubject(createSubjectDto));
    }

}
