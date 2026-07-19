package online.prepquiz.Prep.Quiz.chapter;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chapters")
public class ChapterController {
    private final ChapterService chapterService;
    @GetMapping(params = "subjectId")
    ResponseEntity<List<ChapterDto>> getChapterBySubjectId(@RequestParam Long subjectId){
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChapterBySubjectId(subjectId));
    }

    @PostMapping
    ResponseEntity<ChapterDto> createChapter(@RequestBody CreateChapterDto createChapterDto){
       return ResponseEntity.status(HttpStatus.CREATED).body(chapterService.createChapter(createChapterDto));
    }
}
