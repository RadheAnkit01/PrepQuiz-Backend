package online.prepquiz.Prep.Quiz.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserResponseDto {
    private String jwt;
}
