package com.example.handler;

import com.example.dto.ExceptionResponse;
import com.example.exeptions.ExpiredTokenException;
import com.example.exeptions.InvalidCredentialsException;
import com.example.exeptions.WrongAuthenticationTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ServicesExceptionsHandler extends ResponseEntityExceptionHandler {
    private static final String STRING_VALUE_ERROR = "error";
    private static final String DEFAULT_EXCEPTION_MASSAGE = "Wrong passed data";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exception,
                                                                           HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now());

        Map<String, String> errors = getErrorsMapFromException(exception);

        response.setErrors(errors);

        return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentialsException(InvalidCredentialsException exception,
                                                                               HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now());

        Map<String, String> errors = getErrorsMapFromException(exception);

        response.setErrors(errors);

        return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(response);
    }

    @ExceptionHandler(WrongAuthenticationTokenException.class)
    public ResponseEntity<ExceptionResponse> handleWrongAuthenticationTokenException(WrongAuthenticationTokenException exception,
                                                                                     HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now());

        Map<String, String> errors = getErrorsMapFromException(exception);

        response.setErrors(errors);

        return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(response);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredTokenException(ExpiredTokenException exception,
                                                                         HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now());

        Map<String, String> errors = getErrorsMapFromException(exception);

        response.setErrors(errors);

        return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDateTime.now());
        response.setErrors(ex.getAllErrors().stream()
              .collect(Collectors.toMap(
                    e -> ((FieldError) e).getField(),
                    e -> Optional.ofNullable(e.getDefaultMessage()).orElse(DEFAULT_EXCEPTION_MASSAGE)
              )));

        return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(response);
    }

    private Map<String, String> getErrorsMapFromException(RuntimeException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(STRING_VALUE_ERROR, exception.getMessage());
        return errors;
    }
}
