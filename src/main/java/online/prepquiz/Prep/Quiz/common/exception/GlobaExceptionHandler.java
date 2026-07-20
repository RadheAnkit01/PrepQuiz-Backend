package online.prepquiz.Prep.Quiz.common.exception;

//import tools.jackson.databind.JsonMappingException;
//import tools.jackson.databind.exc.InvalidFormatException;
//import tools.jackson.databind.JsonMappingException;
//import tools.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import online.prepquiz.Prep.Quiz.common.dto.ErrorResponseDto;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

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


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex
    ) {

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException invalidFormatException
                && invalidFormatException.getTargetType().isEnum()) {

            String fieldName = "unknown";

            if (!invalidFormatException.getPath().isEmpty()) {
                fieldName = invalidFormatException.getPath()
                        .get(0)
                        .getFieldName();
            }

            String invalidValue = String.valueOf(invalidFormatException.getValue());

            String allowedValues = Arrays.stream(
                            invalidFormatException.getTargetType().getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest().body(
                    ErrorResponseDto.builder()
                            .status(HttpStatus.BAD_REQUEST.value())
                            .message(String.format(
                                    "Invalid value '%s' for field '%s'. Allowed values are: [%s].",
                                    invalidValue,
                                    fieldName,
                                    allowedValues
                            ))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }


        return ResponseEntity.badRequest().body(
                ErrorResponseDto.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Malformed JSON request. Possibly something wrong in JSON request.")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }




    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodValidation(
            HandlerMethodValidationException ex
    ) {

        String message = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                ErrorResponseDto.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Malformed JSON request : " + message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }


}
