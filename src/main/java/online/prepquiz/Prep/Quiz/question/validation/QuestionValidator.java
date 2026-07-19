package online.prepquiz.Prep.Quiz.question.validation;

import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.common.exception.DuplicateResourceException;
import online.prepquiz.Prep.Quiz.common.exception.InvalidQuestionException;
import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateOptionDto;
import online.prepquiz.Prep.Quiz.question.dto.common.OptionRequest;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;
import online.prepquiz.Prep.Quiz.question.repository.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuestionValidator {

    private final QuestionRepository questionRepository;

    public void validateForCreate(CreateQuestionDto request) {

        validateDuplicateQuestion(
                request.getChapterId(),
                request.getText()
        );

        validateOptions(
                request.getQuestionType(),
                request.getOptions()
        );
    }

    public void validateForUpdate(
            Long questionId,
            Long chapterId,
            String text,
            QuestionType questionType,
            List<UpdateOptionDto> options
    ) {

        validateDuplicateQuestionForUpdate(
                questionId,
                chapterId,
                text
        );

        validateOptions(
                questionType,
                options
        );
    }

    // private methods...

    private void validateDuplicateQuestion(
            Long chapterId,
            String text
    ) {

        if (questionRepository.existsByChapterIdAndTextIgnoreCase(chapterId, text)) {

            throw new DuplicateResourceException(
                    "Question already exists in this chapter."
            );
        }
    }


    private void validateDuplicateQuestionForUpdate(
            Long questionId,
            Long chapterId,
            String text
    ) {

        boolean exists = questionRepository
                .existsByChapterIdAndTextIgnoreCaseAndIdNot(
                        chapterId,
                        text,
                        questionId
                );

        System.out.println("QuestionId = " + questionId);
        System.out.println("ChapterId  = " + chapterId);
        System.out.println("Text       = " + text);
        System.out.println("Exists     = " + exists);

        if (exists) {
            throw new DuplicateResourceException(
                    "Question already exists in this chapter."
            );
        }

//        if (questionRepository.existsByChapterIdAndTextIgnoreCaseAndIdNot(
//                chapterId,
//                text,
//                questionId)) {
//
//            throw new DuplicateResourceException(
//                    "Question already exists in this chapter."
//            );
//        }
    }
    private void validateOptions(
            QuestionType questionType,
            List<? extends OptionRequest> options
    ) {

        validateOptionCount(options);

        validateDisplayOrder(options);

        validateCorrectAnswers(questionType, options);
    }

    private void validateOptionCount(
            List<? extends OptionRequest> options
    ) {

        if (options == null || options.isEmpty()) {

            throw new InvalidQuestionException(
                    "Question must contain options."
            );
        }

        if (options.size() < 2) {

            throw new InvalidQuestionException(
                    "Question must contain at least two options."
            );
        }

        if (options.size() > 6) {

            throw new InvalidQuestionException(
                    "Question cannot contain more than six options."
            );
        }
    }


    private void validateDisplayOrder(
            List<? extends OptionRequest> options
    ) {

        Set<Integer> displayOrders = new HashSet<>();

        for (OptionRequest option : options) {

            if (!displayOrders.add(option.getDisplayOrder())) {

                throw new InvalidQuestionException(
                        "Duplicate display order found."
                );
            }
        }
    }

    private void validateCorrectAnswers(
            QuestionType questionType,
            List<? extends OptionRequest> options
    ) {

        long correctAnswers = options.stream()
                .filter(OptionRequest::getCorrect)
                .count();

        switch (questionType) {

            case SINGLE_CHOICE -> validateSingleChoice(correctAnswers);

            case MULTIPLE_CHOICE -> validateMultipleChoice(correctAnswers);

            case TRUE_FALSE -> validateTrueFalse(correctAnswers, options.size());
        }
    }
    private void validateSingleChoice(long correctAnswers) {

        if (correctAnswers != 1) {

            throw new InvalidQuestionException(
                    "Single choice question must contain exactly one correct answer."
            );
        }
    }

    private void validateMultipleChoice(long correctAnswers) {

        if (correctAnswers < 2) {

            throw new InvalidQuestionException(
                    "Multiple choice question must contain at least two correct answers."
            );
        }
    }

    private void validateTrueFalse(long correctAnswers,
                                   int optionCount) {

        if (optionCount != 2) {

            throw new InvalidQuestionException(
                    "True/False question must contain exactly two options."
            );
        }

        if (correctAnswers != 1) {

            throw new InvalidQuestionException(
                    "True/False question must contain exactly one correct answer."
            );
        }
    }

}