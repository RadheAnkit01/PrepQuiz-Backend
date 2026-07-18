package online.prepquiz.Prep.Quiz.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserDto registerUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(registerUserDto));
    }

    @PostMapping("/login")
    ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginUser(loginUserDto));
    }
}
