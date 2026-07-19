package online.prepquiz.Prep.Quiz.assessment.mapper;

import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentQuestionResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentQuestionDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentQuestionDto;
import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;
import online.prepquiz.Prep.Quiz.assessment.entity.AssessmentQuestion;
import org.mapstruct.*;
@Mapper(componentModel = "spring")
public interface AssessmentQuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessment", ignore = true)
    @Mapping(target = "question", ignore = true)
    AssessmentQuestion toEntity(
            CreateAssessmentQuestionDto request
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessment", ignore = true)
    @Mapping(target = "question", ignore = true)
    AssessmentQuestion toEntity(
            UpdateAssessmentQuestionDto request
    );

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessment", ignore = true)
    @Mapping(target = "question", ignore = true)
    void updateEntity(
            UpdateAssessmentQuestionDto request,
            @MappingTarget AssessmentQuestion question
    );
    @Mapping(
            target = "questionId",
            source = "question.id"
    )
    @Mapping(
            target = "questionText",
            source = "question.text"
    )
    AssessmentQuestionResponseDto toResponse(
            AssessmentQuestion assessmentQuestion
    );

}
