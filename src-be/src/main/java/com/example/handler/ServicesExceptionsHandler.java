package com.example.handler;

import com.example.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@ControllerAdvice
public class ServicesExceptionsHandler extends ResponseEntityExceptionHandler {
    private static final String STRING_VALUE_ERROR = "error";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exception,
                                                                           HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(new LinkedHashMap<>() {{
            put(STRING_VALUE_ERROR, exception.getMessage());
        }});

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
