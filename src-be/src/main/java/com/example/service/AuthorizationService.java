package com.example.service;

import com.example.dto.AuthorizationDto;
import com.example.dto.RegistrationDto;

public interface AuthorizationService {

    String signIn(AuthorizationDto authorizationDto);

    String signUp(RegistrationDto registrationDto);

    String refreshAuthentication(String email);

}
