package online.prepquiz.Prep.Quiz.chapter;

import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.subject.Subject;
import online.prepquiz.Prep.Quiz.subject.SubjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ChapterService {

    private final ChapterRepository chapterRepository;
    private final SubjectService subjectService;


    private ChapterDto mapChapterDto(Chapter chapter){
        return new ChapterDto(chapter.getId(), chapter.getName(), chapter.getSubject().getId(),chapter.getSubject().getName());
    }


    public List<ChapterDto> getChapterBySubjectId(Long subjectId) {
        Subject subject = subjectService.findbyId(subjectId);

        List<Chapter> chapters = chapterRepository.findBySubject(subject);
        List<ChapterDto> chapterDtos = new ArrayList<>();
        for(Chapter chapter : chapters){
            ChapterDto chapterDto = mapChapterDto(chapter);
            chapterDtos.add(chapterDto);
        }
        return  chapterDtos;
    }


    public ChapterDto createChapter(CreateChapterDto createChapterDto) {
        Subject subject = subjectService.findbyId(createChapterDto.getSubjectId());
        Chapter chapter = new Chapter();
        chapter.setName(createChapterDto.getName());
        chapter.setSubject(subject);
        Chapter savedChapter = chapterRepository.save(chapter);
        return  mapChapterDto(savedChapter);
    }
}
