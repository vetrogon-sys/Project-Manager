package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Not valid email value")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
          message = "Password must be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    private String password;
    @Size(min=2, max=60, message = "First name cannot be blank")
    private String firstName;
    @Size(min=2, max=60, message = "Last name cannot be blank")
    private String lastName;

}
