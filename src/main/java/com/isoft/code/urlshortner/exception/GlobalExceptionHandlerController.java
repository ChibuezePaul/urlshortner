package com.isoft.code.urlshortner.exception;

import com.isoft.code.urlshortner.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) throws IOException {
        String validationMessages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(". "));
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(validationMessages)
                        .build()
                );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException ex) throws IOException {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.builder()
                        .message(ex.getMessage())
                        .build()
                );
    }
}
