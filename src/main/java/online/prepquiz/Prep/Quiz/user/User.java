package online.prepquiz.Prep.Quiz.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import online.prepquiz.Prep.Quiz.common.baseclass.Auditable;
import online.prepquiz.Prep.Quiz.course.Course;

import java.util.UUID;

@Table(name = "users")
@Entity
@Getter
@Setter
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
