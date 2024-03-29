package com.example.controller;

import com.example.dto.AuthorizationDto;
import com.example.dto.JWTTokenDto;
import com.example.dto.RegistrationDto;
import com.example.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/signin")
    public ResponseEntity<JWTTokenDto> login(@Valid @RequestBody AuthorizationDto authorizationDto) {
        return ResponseEntity.ok(authorizationService.signIn(authorizationDto));
    }

    @PostMapping("/signup")
    @PermitAll
    public ResponseEntity<JWTTokenDto> register(@Valid @RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(authorizationService.signUp(registrationDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTTokenDto> refresh(HttpServletRequest request) {
        return ResponseEntity.ok(authorizationService.refreshAuthentication(request.getRemoteUser()));
    }

}
