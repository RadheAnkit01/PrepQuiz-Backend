package online.prepquiz.Prep.Quiz.quiz;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.chapter.Chapter;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_seq")
    @SequenceGenerator(
            name = "quiz_seq",
            sequenceName = "quiz_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;


}