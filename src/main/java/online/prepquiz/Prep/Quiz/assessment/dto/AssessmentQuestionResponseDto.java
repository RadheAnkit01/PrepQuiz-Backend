package online.prepquiz.Prep.Quiz.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentQuestionResponseDto {

    private Long id;

    private Long questionId;

    private String questionText;

    private Integer displayOrder;

    private Integer marks;

    private Integer negativeMarks;
}