package online.prepquiz.Prep.Quiz.subject;

import online.prepquiz.Prep.Quiz.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findByCourse(Course course);
}
