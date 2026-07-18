package online.prepquiz.Prep.Quiz.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.course.Course;

@Getter
@Setter
@AllArgsConstructor
public class CreateSubjectDto {
    private String name;
    private Long courseId;
}
