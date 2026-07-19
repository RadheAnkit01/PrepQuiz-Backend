package online.prepquiz.Prep.Quiz.subject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.chapter.Chapter;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;
import online.prepquiz.Prep.Quiz.course.Course;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects",uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "name"})})
@Getter
@Setter
public class Subject extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_seq")
    @SequenceGenerator(
            name = "subject_seq",
            sequenceName = "subject_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
