package online.prepquiz.Prep.Quiz.question.mapper;

import online.prepquiz.Prep.Quiz.question.dto.CreateQuestionDto;
import online.prepquiz.Prep.Quiz.question.dto.QuestionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateQuestionDto;
import online.prepquiz.Prep.Quiz.question.entity.Question;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = OptionMapper.class
)
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "status", ignore = true)
    Question toEntity(CreateQuestionDto request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "options", ignore = true)
    void updateEntity(UpdateQuestionDto request, @MappingTarget Question question);

    @Mapping(source = "chapter.id", target = "chapterId")
    QuestionResponseDto toResponse(Question question);
}