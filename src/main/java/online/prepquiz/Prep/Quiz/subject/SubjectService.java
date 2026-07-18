package online.prepquiz.Prep.Quiz.subject;

import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.course.Course;
import online.prepquiz.Prep.Quiz.course.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor


public class SubjectService {
    private final CourseService courseService;
    private final SubjectRepository subjectRepository;

    private SubjectDto mapSubject(Subject subject){
        return new SubjectDto(subject.getId(),subject.getName(),subject.getCourse().getId(),subject.getCourse().getName());
    }
    public List<SubjectDto> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(Subject subject : subjects){
            SubjectDto subjectDto = mapSubject(subject);
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    public List<SubjectDto> getAllSubjectForCourseId(Long courseId) {
        Course course = courseService.getById(courseId);
        List<Subject> subjects = subjectRepository.findByCourse(course);
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(Subject subject: subjects){
            SubjectDto subjectDto = mapSubject(subject);
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    public SubjectDto createSubject(CreateSubjectDto createSubjectDto) {
        Course course = courseService.getById(createSubjectDto.getCourseId());
        Subject subject = new Subject();
        subject.setName(createSubjectDto.getName());
        subject.setCourse(course);

        Subject savedSubject = subjectRepository.save(subject);
        return mapSubject(savedSubject);
    }
}
