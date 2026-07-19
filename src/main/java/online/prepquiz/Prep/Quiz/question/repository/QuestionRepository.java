package online.prepquiz.Prep.Quiz.question.repository;

import online.prepquiz.Prep.Quiz.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    boolean existsByChapterIdAndTextIgnoreCase(Long chapterId, String text);
    boolean existsByChapterIdAndTextIgnoreCaseAndIdNot(
            Long chapterId,
            String text,
            Long id
    );
    List<Question> findByChapterId(Long chapterId);
}
