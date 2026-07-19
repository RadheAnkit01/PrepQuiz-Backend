package online.prepquiz.Prep.Quiz.question.mapper;

import online.prepquiz.Prep.Quiz.question.dto.CreateOptionDto;
import online.prepquiz.Prep.Quiz.question.dto.OptionResponseDto;
import online.prepquiz.Prep.Quiz.question.dto.UpdateOptionDto;
import online.prepquiz.Prep.Quiz.question.entity.Option;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    Option toEntity(CreateOptionDto request);
    Option toEntity(UpdateOptionDto request);
    OptionResponseDto toResponse(Option option);

    List<OptionResponseDto> toResponseList(List<Option> options);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    void updateEntity(
            @MappingTarget Option option,
            UpdateOptionDto request
    );
}