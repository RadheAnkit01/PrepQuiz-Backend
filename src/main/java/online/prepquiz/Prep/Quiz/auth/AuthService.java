package online.prepquiz.Prep.Quiz.auth;

import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.common.enums.Role;
import online.prepquiz.Prep.Quiz.security.JwtService;
import online.prepquiz.Prep.Quiz.user.User;
import online.prepquiz.Prep.Quiz.user.UserRepository;
import online.prepquiz.Prep.Quiz.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterUserResponseDto registerUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setName(registerUserDto.getName());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(Role.USER);
        user.setCourse(null);
        User savedUser = userRepository.save(user);
        return new RegisterUserResponseDto(savedUser.getName(), savedUser.getEmail());
    }

    public LoginUserResponseDto loginUser(LoginUserDto loginUserDto) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(),loginUserDto.getPassword())
        );

        String jwt = jwtService.generateJwtToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));

        return  new LoginUserResponseDto(jwt);
    }
}
