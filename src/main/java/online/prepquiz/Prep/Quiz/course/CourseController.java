package online.prepquiz.Prep.Quiz.course;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    ResponseEntity<List<CourseDto>> getCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourses());
    }

    @GetMapping("/{id}")
    ResponseEntity<CourseDto> getCourses(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCoursesById(id));
    }

    @PostMapping
    ResponseEntity<CourseDto> createCourse(@RequestBody CreateCourseDto createCourseDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(createCourseDto));
    }

}
