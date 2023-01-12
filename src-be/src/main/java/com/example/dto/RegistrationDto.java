package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @Email(message = "Not valid email value")
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
