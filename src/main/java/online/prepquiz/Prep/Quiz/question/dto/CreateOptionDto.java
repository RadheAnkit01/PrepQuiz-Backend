package online.prepquiz.Prep.Quiz.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import online.prepquiz.Prep.Quiz.question.dto.common.OptionRequest;

@Data
public class CreateOptionDto implements OptionRequest {
    @NotBlank
    private String text;

    @NotNull
    private Boolean correct;

    @NotNull
    @Positive
    private Integer displayOrder;
}
