package online.prepquiz.Prep.Quiz.question.repository;

import online.prepquiz.Prep.Quiz.question.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option,Long> {
}
