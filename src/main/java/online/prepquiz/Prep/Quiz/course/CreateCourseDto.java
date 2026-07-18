package online.prepquiz.Prep.Quiz.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCourseDto {
    private String name;
    private String description;
}
