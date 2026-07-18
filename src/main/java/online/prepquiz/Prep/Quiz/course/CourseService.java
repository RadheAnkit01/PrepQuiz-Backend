package online.prepquiz.Prep.Quiz.course;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<CourseDto> getCourses() {
        List<Course> lists = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for(Course course : lists){
            CourseDto courseDto = new CourseDto(course.getId(),course.getName(), course.getDescription());
            courseDtos.add(courseDto);
        }
        return courseDtos;
    }

    public CourseDto createCourse(CreateCourseDto createCourseDto) {
        Course course = new Course();
        course.setName(createCourseDto.getName().toUpperCase());
        course.setDescription(createCourseDto.getDescription());
        Course savedCourse = courseRepository.save(course);
        return mapCourseDto(savedCourse);
    }

    private CourseDto mapCourseDto(Course savedCourse) {
        return new CourseDto(savedCourse.getId(), savedCourse.getName(), savedCourse.getDescription());
    }

    public CourseDto getCoursesById(Long id) {
        Course course = getById(id);
        return mapCourseDto(course);
    }

    public Course getById(Long id){
        return courseRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Course Not Found For ID : " + id)
        );
    }
}
