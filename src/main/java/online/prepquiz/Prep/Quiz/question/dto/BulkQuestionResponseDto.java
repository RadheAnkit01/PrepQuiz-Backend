package online.prepquiz.Prep.Quiz.question.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkQuestionResponseDto {

    private Integer total;

    private Integer success;

    private Integer failed;

    private List<QuestionResponseDto> questions;

}