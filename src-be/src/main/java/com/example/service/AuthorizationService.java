package com.example.service;

import com.example.dto.AuthorizationDto;
import com.example.dto.JWTTokenDto;
import com.example.dto.RegistrationDto;

public interface AuthorizationService {

    JWTTokenDto signIn(AuthorizationDto authorizationDto);

    JWTTokenDto signUp(RegistrationDto registrationDto);

    JWTTokenDto refreshAuthentication(String email);

}
