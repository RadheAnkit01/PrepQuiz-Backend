package online.prepquiz.Prep.Quiz.question.entity;

import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "options",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_option_question_display_order",
                        columnNames = {"question_id", "display_order"}
                )
        },
        indexes = {
                @Index(name = "idx_option_question", columnList = "question_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Option {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "option_seq"
    )
    @SequenceGenerator(
            name = "option_seq",
            sequenceName = "option_seq",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "question_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_option_question")
    )
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private Boolean correct;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}