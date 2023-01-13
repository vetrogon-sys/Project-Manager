package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDto {

    @Email(message = "Not valid email value")
    private String email;
    private String password;

}
