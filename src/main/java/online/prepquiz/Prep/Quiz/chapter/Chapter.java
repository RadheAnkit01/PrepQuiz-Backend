package online.prepquiz.Prep.Quiz.chapter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;
import online.prepquiz.Prep.Quiz.subject.Subject;

@Entity
@Table(
        name = "chapters",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"subject_id", "name"})
        }
)
@Getter
@Setter
public class Chapter extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapter_seq")
    @SequenceGenerator(
            name = "chapter_seq",
            sequenceName = "chapter_sequence",
            initialValue = 10000,
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id",nullable = false)
    private Subject subject;


}
