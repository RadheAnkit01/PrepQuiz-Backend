package online.prepquiz.Prep.Quiz.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.course.Course;

//@AllArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class SubjectDto {
    private Long id;
    private String name;
    private Long courseId;
    private String courseName;
}
