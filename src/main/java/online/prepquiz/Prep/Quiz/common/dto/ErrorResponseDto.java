package online.prepquiz.Prep.Quiz.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
