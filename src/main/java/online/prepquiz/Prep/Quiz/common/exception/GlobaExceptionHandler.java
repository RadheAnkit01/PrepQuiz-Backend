package online.prepquiz.Prep.Quiz.common.exception;

import online.prepquiz.Prep.Quiz.common.dto.ErrorResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobaExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateEntry(DataIntegrityViolationException exception){
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.CONFLICT.value(),
                exception.getMostSpecificCause().getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HashMap<String,String> fieldError = new HashMap<>();

        ex.getFieldErrors().forEach(err -> {
            fieldError.put(err.getField(),err.getDefaultMessage());
        });

        StringBuilder errorMessage = new StringBuilder();
        for(String field : fieldError.keySet()){
            errorMessage.append(field).append(" : ").append(fieldError.get(field)).append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        "INVALID_INPUT : " + errorMessage.toString(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex){
        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST : "+ ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(
            ResourceNotFoundException ex) {

        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateResourceException(
            DuplicateResourceException ex
    ) {

        ErrorResponseDto error = new ErrorResponseDto(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex
    ) {

        String message = String.format(
                "Invalid value '%s' for parameter '%s'.",
                ex.getValue(),
                ex.getName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.value(),
                        message,
                        LocalDateTime.now()
                ));
    }

}
