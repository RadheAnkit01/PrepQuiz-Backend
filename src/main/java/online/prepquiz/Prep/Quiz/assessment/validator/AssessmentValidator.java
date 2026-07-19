package online.prepquiz.Prep.Quiz.assessment.validator;

import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;

public interface AssessmentValidator {

    void validateCreate(CreateAssessmentDto request);

    void validateUpdate(
            Assessment assessment,
            UpdateAssessmentDto request
    );

    void validatePublish(
            Assessment assessment
    );

    void validateArchive(
            Assessment assessment
    );

    void validateDelete(Assessment assessment);
}