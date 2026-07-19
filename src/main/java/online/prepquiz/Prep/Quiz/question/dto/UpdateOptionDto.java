package online.prepquiz.Prep.Quiz.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import online.prepquiz.Prep.Quiz.question.dto.common.OptionRequest;

@Data
public class UpdateOptionDto implements OptionRequest {

    /**
     * Null = New Option
     * Not Null = Existing Option
     */
    private Long id;

    @NotBlank(message = "Option text is required.")
    private String text;

    @NotNull(message = "Correct flag is required.")
    private Boolean correct;

    @NotNull(message = "Display order is required.")
    @Positive(message = "Display order must be greater than 0.")
    private Integer displayOrder;
}