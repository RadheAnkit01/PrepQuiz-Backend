package online.prepquiz.Prep.Quiz.chapter;

import online.prepquiz.Prep.Quiz.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    List<Chapter> findBySubject(Subject subject);

}
