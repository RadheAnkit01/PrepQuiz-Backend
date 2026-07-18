package online.prepquiz.Prep.Quiz.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private String description;
}
