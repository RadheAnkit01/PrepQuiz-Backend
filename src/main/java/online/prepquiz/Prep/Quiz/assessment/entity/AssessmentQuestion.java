package online.prepquiz.Prep.Quiz.assessment.entity;

import jakarta.persistence.*;
import lombok.*;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;
import online.prepquiz.Prep.Quiz.question.entity.Question;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "assessment_questions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assessment_question",
                        columnNames = {
                                "assessment_id",
                                "question_id"
                        }
                ),
                @UniqueConstraint(
                        name = "uk_assessment_display_order",
                        columnNames = {
                                "assessment_id",
                                "display_order"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_assessment_question_assessment",
                        columnList = "assessment_id"
                ),
                @Index(
                        name = "idx_assessment_question_question",
                        columnList = "question_id"
                )
        }
)
public class AssessmentQuestion extends Auditable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assessment_question_seq"
    )
    @SequenceGenerator(
            name = "assessment_question_seq",
            sequenceName = "assessment_question_seq",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "assessment_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_assessment_question_assessment"
            )
    )
    private Assessment assessment;
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "question_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_assessment_question_question"
            )
    )
    private Question question;
    @Column(
            name = "display_order",
            nullable = false
    )
    private Integer displayOrder;
    @Column(nullable = false)
    private Integer marks;
    @Builder.Default
    @Column(
            name = "negative_marks",
            nullable = false
    )
    private Integer negativeMarks = 0;
}