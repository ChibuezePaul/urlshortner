package com.isoft.code.urlshortner.exception;

import com.isoft.code.urlshortner.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(CustomException ex) throws IOException {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.builder()
                        .message(ex.getMessage())
                        .build()
                );
    }
}
