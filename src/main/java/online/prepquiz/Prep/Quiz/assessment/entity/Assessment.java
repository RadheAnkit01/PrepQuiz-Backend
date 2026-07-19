package online.prepquiz.Prep.Quiz.assessment.entity;

import jakarta.persistence.*;
import lombok.*;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentScopeType;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentStatus;
import online.prepquiz.Prep.Quiz.assessment.enums.AssessmentType;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "assessments",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_assessment_scope_title",
                        columnNames = {
                                "scope_type",
                                "scope_id",
                                "title"
                        }
                )
        },
        indexes = {
                @Index(
                        name = "idx_assessment_scope",
                        columnList = "scope_type, scope_id"
                ),
                @Index(
                        name = "idx_assessment_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_assessment_type",
                        columnList = "assessment_type"
                )
        }
)
public class Assessment extends Auditable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assessment_seq"
    )
    @SequenceGenerator(
            name = "assessment_seq",
            sequenceName = "assessment_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(
            nullable = false,
            length = 200
    )
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "assessment_type",
            nullable = false,
            length = 30
    )
    private AssessmentType assessmentType;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "scope_type",
            nullable = false,
            length = 20
    )
    private AssessmentScopeType scopeType;
    @Column(
            name = "scope_id",
            nullable = false
    )
    private Long scopeId;
    @Column(
            name = "duration_minutes",
            nullable = false
    )
    private Integer durationMinutes;
    @Builder.Default
    @Column(
            name = "shuffle_questions",
            nullable = false
    )
    private Boolean shuffleQuestions = false;
    @Builder.Default
    @Column(
            name = "shuffle_options",
            nullable = false
    )
    private Boolean shuffleOptions = false;
    @Builder.Default
    @Column(
            name = "show_answers_after_submit",
            nullable = false
    )
    private Boolean showAnswersAfterSubmit = false;
    @Builder.Default
    @Column(nullable = false)
    private Integer version = 1;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private AssessmentStatus status = AssessmentStatus.DRAFT;
    @OneToMany(
            mappedBy = "assessment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    @OrderBy("displayOrder ASC")
    private List<AssessmentQuestion> assessmentQuestions = new ArrayList<>();

}