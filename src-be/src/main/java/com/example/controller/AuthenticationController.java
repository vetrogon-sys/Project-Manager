package com.example.controller;

import com.example.dto.AuthorizationDto;
import com.example.dto.RegistrationDto;
import com.example.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
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
    public String login(@Valid @RequestBody AuthorizationDto authorizationDto) {
        return authorizationService.signIn(authorizationDto);
    }

    @PostMapping("/signup")
    @PermitAll
    public String register(@Valid @RequestBody RegistrationDto registrationDto) {
        return authorizationService.signUp(registrationDto);
    }

    @PostMapping("/refresh")
    public String refresh(HttpServletRequest request) {
        return authorizationService.refreshAuthentication(request.getRemoteUser());
    }

}
