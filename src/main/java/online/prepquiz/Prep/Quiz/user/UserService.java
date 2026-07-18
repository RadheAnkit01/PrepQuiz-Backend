package online.prepquiz.Prep.Quiz.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.common.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDto mapToUserDto(User user){
        return new UserDto(user.getName(), user.getEmail(), user.getCourse());
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            UserDto userDto = mapToUserDto(user);
            userDtos.add(userDto);
        }
        return  userDtos;
    }

    public UserDto getMyDetails() {
        User user= getLoggedInUser();
        return mapToUserDto(user);
    }

    protected User getLoggedInUser(){
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found for email : " + email));
    }

    @Transactional
    public UserDto updateMyDetails(UpdateUserDto updateUserDto) {
        User user = getLoggedInUser();
        user.setName(updateUserDto.getName());
        user.setCourse(updateUserDto.getCourse());
        return mapToUserDto(user);
    }
}
