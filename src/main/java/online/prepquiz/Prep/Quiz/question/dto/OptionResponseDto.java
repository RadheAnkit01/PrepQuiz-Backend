package online.prepquiz.Prep.Quiz.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OptionResponseDto {
    private Long id;
    private String text;
    private Boolean correct;
    private Integer displayOrder;
}
