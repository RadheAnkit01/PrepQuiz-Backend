package online.prepquiz.Prep.Quiz.question.entity;

import jakarta.persistence.*;
import lombok.*;
import online.prepquiz.Prep.Quiz.chapter.Chapter;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;
import online.prepquiz.Prep.Quiz.question.enums.Difficulty;
import online.prepquiz.Prep.Quiz.question.enums.QuestionStatus;
import online.prepquiz.Prep.Quiz.question.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_chapter_text",
                        columnNames = {"chapter_id", "text"}
                )
        },
        indexes = {
                @Index(name = "idx_question_chapter", columnList = "chapter_id"),
                @Index(name = "idx_question_status", columnList = "status"),
                @Index(name = "idx_question_difficulty", columnList = "difficulty"),
                @Index(name = "idx_question_type", columnList = "question_type")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends Auditable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_seq"
    )
    @SequenceGenerator(
            name = "question_seq",
            sequenceName = "question_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "chapter_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_question_chapter")
    )
    private Chapter chapter;

    @Column(
            name = "text",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "question_type",
            nullable = false,
            length = 30
    )
    private QuestionType questionType;

    @Column(name = "is_pyq", nullable = false)
    @Builder.Default
    private Boolean pyq = false;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private Difficulty difficulty;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    @Builder.Default
    private QuestionStatus status = QuestionStatus.ACTIVE;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Option> options = new ArrayList<>();

}