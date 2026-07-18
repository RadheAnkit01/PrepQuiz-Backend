package online.prepquiz.Prep.Quiz.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDto {
    private String name;
    private String email;
    private String password;
}
