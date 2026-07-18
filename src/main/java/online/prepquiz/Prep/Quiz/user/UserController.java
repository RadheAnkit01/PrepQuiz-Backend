package online.prepquiz.Prep.Quiz.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    ResponseEntity<UserDto>  getMyDetails(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyDetails());
    }
    @PutMapping("/me")
    ResponseEntity<UserDto>  updateMyDetails(@RequestBody UpdateUserDto updateUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateMyDetails(updateUserDto));
    }

    @GetMapping
    ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }
}

