package online.prepquiz.Prep.Quiz.assessment.validator;

import lombok.RequiredArgsConstructor;
import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentQuestionRequest;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.assessment.repository.AssessmentRepository;
import online.prepquiz.Prep.Quiz.chapter.ChapterRepository;
import online.prepquiz.Prep.Quiz.common.exception.BadRequestException;
import online.prepquiz.Prep.Quiz.common.exception.ResourceNotFoundException;
import online.prepquiz.Prep.Quiz.course.CourseRepository;
import online.prepquiz.Prep.Quiz.question.entity.Question;
import online.prepquiz.Prep.Quiz.question.repository.QuestionRepository;
import online.prepquiz.Prep.Quiz.subject.SubjectRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class AssessmentValidatorImpl implements AssessmentValidator {

    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final ChapterRepository chapterRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void validateCreate(CreateAssessmentDto request) {

        validateScope(
                request.getScopeType(),
                request.getScopeId()
        );

        validateDuplicateTitle(
                request.getScopeType(),
                request.getScopeId(),
                request.getTitle()
        );

        validateAssessmentQuestions(
                request.getScopeType(),
                request.getScopeId(),
                request.getAssessmentQuestions()
        );

        validatePassingMarks(
                request.getPassingMarks(),
                request.getAssessmentQuestions()
        );
    }

    @Override
    public void validateUpdate(
            Assessment assessment,
            UpdateAssessmentDto request
    ) {

        if (assessment.getStatus() != AssessmentStatus.DRAFT) {
            throw new BadRequestException(
                    "Only draft assessments can be updated."
            );
        }

        validateScope(
                request.getScopeType(),
                request.getScopeId()
        );

        validateDuplicateTitle(
                assessment.getId(),
                request.getScopeType(),
                request.getScopeId(),
                request.getTitle()
        );

        validateAssessmentQuestionsUpdate(
                request.getScopeType(),
                request.getScopeId(),
                request.getAssessmentQuestions()
        );

        validatePassingMarks(
                request.getPassingMarks(),
                request.getAssessmentQuestions()
        );
    }

    @Override
    public void validatePublish(Assessment assessment) {

        if (assessment.getStatus() != AssessmentStatus.DRAFT) {

            throw new BadRequestException(
                    "Only draft assessments can be published."
            );
        }

        if (assessment.getAssessmentQuestions().isEmpty()) {

            throw new BadRequestException(
                    "Assessment must contain at least one question."
            );
        }
    }

    @Override
    public void validateArchive(Assessment assessment) {

        if (assessment.getStatus() != AssessmentStatus.PUBLISHED) {

            throw new BadRequestException(
                    "Only published assessments can be archived."
            );
        }
    }

    @Override
    public void validateDelete(Assessment assessment) {

        if (assessment.getStatus() != AssessmentStatus.DRAFT) {

            throw new BadRequestException(
                    "Only draft assessments can be deleted."
            );
        }
    }

    //-----------------
    //Helper
    //----------------


    private void validateScope(AssessmentScopeType scopeType, Long scopeId) {
        switch (scopeType) {
            case COURSE -> {
                if (!courseRepository.existsById(scopeId)) {

                    throw new ResourceNotFoundException(
                            "Course not found with id : " + scopeId
                    );
                }
            }
            case SUBJECT -> {
                if (!subjectRepository.existsById(scopeId)) {

                    throw new ResourceNotFoundException(
                            "Subject not found with id : " + scopeId
                    );
                }
            }
            case CHAPTER -> {
                if (!chapterRepository.existsById(scopeId)) {
                    throw new ResourceNotFoundException(
                            "Chapter not found with id : " + scopeId
                    );
                }
            }
            default -> throw new BadRequestException(
                    "Invalid assessment scope."
            );
        }
    }

    private void validateDuplicateTitle(
            AssessmentScopeType scopeType,
            Long scopeId,
            String title
    ) {

        if (assessmentRepository
                .existsByScopeTypeAndScopeIdAndTitleIgnoreCase(
                        scopeType,
                        scopeId,
                        title
                )) {

            throw new BadRequestException(
                    "Assessment title already exists."
            );
        }
    }

    private void validateDuplicateTitle(
            Long assessmentId,
            AssessmentScopeType scopeType,
            Long scopeId,
            String title
    ) {

        if (assessmentRepository
                .existsByScopeTypeAndScopeIdAndTitleIgnoreCaseAndIdNot(
                        scopeType,
                        scopeId,
                        title,
                        assessmentId
                )) {

            throw new BadRequestException(
                    "Assessment title already exists."
            );
        }
    }

    private void validateAssessmentQuestions(
            AssessmentScopeType scopeType,
            Long scopeId,
            List<? extends AssessmentQuestionRequest> questions
    ) {

        if (questions == null || questions.isEmpty()) {

            throw new BadRequestException(
                    "Assessment must contain at least one question."
            );
        }

        Set<Long> questionIds = new HashSet<>();

        Set<Integer> displayOrders = new HashSet<>();

        for (AssessmentQuestionRequest question : questions) {

            if (!questionIds.add(question.getQuestionId())) {

                throw new BadRequestException(
                        "Duplicate question found."
                );
            }

            if (!displayOrders.add(question.getDisplayOrder())) {

                throw new BadRequestException(
                        "Duplicate display order found."
                );
            }

            validateQuestionBelongsToScope(
                    scopeType,
                    scopeId,
                    new ArrayList<>(questionIds)
            );

            validateMarks(
                    question.getMarks(),
                    question.getNegativeMarks()
            );
        }
    }
    private void validateMarks(
            Integer marks,
            Integer negativeMarks
    ) {

        if (marks == null || marks <= 0) {

            throw new BadRequestException(
                    "Marks must be greater than zero."
            );
        }

        if (negativeMarks != null && negativeMarks < 0) {

            throw new BadRequestException(
                    "Negative marks cannot be less than zero."
            );
        }

        if (negativeMarks != null && negativeMarks > marks) {

            throw new BadRequestException(
                    "Negative marks cannot exceed marks."
            );
        }
    }

    private void validateQuestionBelongsToScope(
            AssessmentScopeType scopeType,
            Long scopeId,
            List<Long> questionIds
    ) {

        List<Question> questions =
                questionRepository.findAllById(questionIds);

        if (questions.size() != questionIds.size()) {

            throw new ResourceNotFoundException(
                    "One or more questions do not exist."
            );
        }

        for (Question question : questions) {

            switch (scopeType) {

                case COURSE -> {

                    if (!question.getChapter()
                            .getSubject()
                            .getCourse()
                            .getId()
                            .equals(scopeId)) {

                        throw new BadRequestException(
                                "Question " + question.getId()
                                        + " does not belong to the selected course."
                        );
                    }
                }

                case SUBJECT -> {

                    if (!question.getChapter()
                            .getSubject()
                            .getId()
                            .equals(scopeId)) {

                        throw new BadRequestException(
                                "Question " + question.getId()
                                        + " does not belong to the selected subject."
                        );
                    }
                }

                case CHAPTER -> {

                    if (!question.getChapter()
                            .getId()
                            .equals(scopeId)) {

                        throw new BadRequestException(
                                "Question " + question.getId()
                                        + " does not belong to the selected chapter."
                        );
                    }
                }
            }
        }
    }

    private void validateAssessmentQuestionsUpdate(
            AssessmentScopeType scopeType,
            Long scopeId,
            List<? extends AssessmentQuestionRequest> questions
    ) {

        if (questions == null || questions.isEmpty()) {

            throw new BadRequestException(
                    "Assessment must contain at least one question."
            );
        }

        Set<Long> questionIds = new HashSet<>();
        Set<Integer> displayOrders = new HashSet<>();

        for (AssessmentQuestionRequest question : questions) {

            if (!questionIds.add(question.getQuestionId())) {

                throw new BadRequestException(
                        "Duplicate question found."
                );
            }

            if (!displayOrders.add(question.getDisplayOrder())) {

                throw new BadRequestException(
                        "Duplicate display order found."
                );
            }

            validateMarks(
                    question.getMarks(),
                    question.getNegativeMarks()
            );
        }

        validateQuestionBelongsToScope(
                scopeType,
                scopeId,
                new ArrayList<>(questionIds)
        );
    }



    private void validatePassingMarks(
            Integer passingMarks,
            List<? extends AssessmentQuestionRequest> questions
    ) {

        if (passingMarks == null) {
            return;
        }

        int totalMarks = questions.stream()
                .mapToInt(AssessmentQuestionRequest::getMarks)
                .sum();

        if (passingMarks < 0) {

            throw new BadRequestException(
                    "Passing marks cannot be negative."
            );
        }

        if (passingMarks > totalMarks) {

            throw new BadRequestException(
                    "Passing marks cannot exceed total marks."
            );
        }
    }

    private void validateDuration(Integer durationMinutes) {

        if (durationMinutes == null || durationMinutes <= 0) {

            throw new BadRequestException(
                    "Duration must be greater than zero."
            );
        }
    }


//    private void validateScopeExists(...){}
//    private void validateDuplicateQuestions(...){}
//    private void validateDuplicateDisplayOrder(...){}
//    private void validateQuestionScope(...){}
//    private void validateQuestionMarks(...){}
//    private void validateHasQuestions(...){}
//    private void validateDraftStatus(...){}
//    private void validatePublishedStatus(...){}


}