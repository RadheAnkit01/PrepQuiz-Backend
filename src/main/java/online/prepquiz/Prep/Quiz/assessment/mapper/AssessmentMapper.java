package online.prepquiz.Prep.Quiz.assessment.mapper;

import online.prepquiz.Prep.Quiz.assessment.dto.AssessmentResponseDto;
import online.prepquiz.Prep.Quiz.assessment.dto.CreateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.dto.UpdateAssessmentDto;
import online.prepquiz.Prep.Quiz.assessment.entity.Assessment;
import online.prepquiz.Prep.Quiz.assessment.entity.AssessmentQuestion;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                AssessmentQuestionMapper.class
        }
)
public interface AssessmentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "assessmentQuestions", ignore = true)
    Assessment toEntity(CreateAssessmentDto request);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "assessmentQuestions", ignore = true)
    void updateEntity(
            UpdateAssessmentDto request,
            @MappingTarget Assessment assessment
    );
    AssessmentResponseDto toResponse(
            Assessment assessment
    );


    @AfterMapping
    default void calculateTotalMarks(
            Assessment assessment,
            @MappingTarget AssessmentResponseDto response
    ) {
        int total = assessment.getAssessmentQuestions()
                .stream()
                .mapToInt(AssessmentQuestion::getMarks)
                .sum();

        response.setTotalMarks(total);
    }
}