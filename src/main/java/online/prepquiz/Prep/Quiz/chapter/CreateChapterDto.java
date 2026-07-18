package online.prepquiz.Prep.Quiz.chapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateChapterDto {
    private String name;
    private Long subjectId;
}
