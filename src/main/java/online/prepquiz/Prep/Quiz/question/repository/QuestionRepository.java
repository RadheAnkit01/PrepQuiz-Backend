package online.prepquiz.Prep.Quiz.question.repository;

import online.prepquiz.Prep.Quiz.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long>, JpaSpecificationExecutor<Question> {
    boolean existsByChapterIdAndTextIgnoreCase(Long chapterId, String text);
    boolean existsByChapterIdAndTextIgnoreCaseAndIdNot(
            Long chapterId,
            String text,
            Long id
    );
    List<Question> findByChapterId(Long chapterId);
}
