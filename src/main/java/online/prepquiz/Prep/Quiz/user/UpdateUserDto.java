package online.prepquiz.Prep.Quiz.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.course.Course;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDto {
    private String name;
    private Course course;
}
