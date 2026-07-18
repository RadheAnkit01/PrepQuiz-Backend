package online.prepquiz.Prep.Quiz.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.course.Course;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private Course course;
}
