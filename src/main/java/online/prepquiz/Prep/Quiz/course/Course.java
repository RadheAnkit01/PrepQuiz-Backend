package online.prepquiz.Prep.Quiz.course;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.subject.Subject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "course_seq")
    @SequenceGenerator(
            name = "course_seq",
            sequenceName = "course_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    private String description;
//    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Subject> subjects = new ArrayList<>();


}
