package online.prepquiz.Prep.Quiz.question;

import online.prepquiz.Prep.Quiz.question.entity.Question;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionScopeType;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecification {

    public static Specification<Question> hasScope(
            QuestionScopeType scopeType,
            Long scopeId
    ) {
        return (root, query, cb) -> {

            if (scopeType == null || scopeId == null) {
                return cb.conjunction();
            }

            return switch (scopeType) {

                case COURSE ->
                        cb.equal(
                                root.get("chapter")
                                        .get("subject")
                                        .get("course")
                                        .get("id"),
                                scopeId
                        );

                case SUBJECT ->
                        cb.equal(
                                root.get("chapter")
                                        .get("subject")
                                        .get("id"),
                                scopeId
                        );

                case CHAPTER ->
                        cb.equal(
                                root.get("chapter")
                                        .get("id"),
                                scopeId
                        );
            };
        };
    }

    public static Specification<Question> hasDifficulty(
            Difficulty difficulty
    ) {

        return (root, query, cb) ->
                difficulty == null
                        ? cb.conjunction()
                        : cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<Question> hasStatus(
            QuestionStatus status
    ) {

        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<Question> hasQuestionType(
            QuestionType type
    ) {

        return (root, query, cb) ->
                type == null
                        ? cb.conjunction()
                        : cb.equal(root.get("questionType"), type);
    }

}