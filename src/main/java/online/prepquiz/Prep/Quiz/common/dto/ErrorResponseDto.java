package online.prepquiz.Prep.Quiz.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
