package com.example.security.handler;

import com.example.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class AccessExceptionsHandlerController {

    @ApiIgnore
    @GetMapping("/access-denied")
    public ResponseEntity<ExceptionResponse> handleAccessDenied(HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setTimestamp(LocalDateTime.now());

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("message", "Access Denied");

        response.setErrors(errors);
        return ResponseEntity
              .status(HttpStatus.FORBIDDEN)
              .body(response);
    }

}
