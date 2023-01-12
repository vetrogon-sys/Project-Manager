package com.example.service.impl;

import com.example.dto.AuthorizationDto;
import com.example.dto.RegistrationDto;
import com.example.entity.User;
import com.example.exeptions.InvalidCredentialsException;
import com.example.mapper.UserMapper;
import com.example.security.service.TokenProvider;
import com.example.service.AuthorizationService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signIn(AuthorizationDto authorizationDto) {
        try {
            String email = authorizationDto.getEmail();
            Authentication authentication
                  = new UsernamePasswordAuthenticationToken(
                  email,
                  authorizationDto.getPassword()
            );
            authenticationManager.authenticate(authentication);
            return tokenProvider.createToken(email, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Authentication exception. Check passed credentials");
        }
    }

    @Override
    public String signUp(RegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        if (userService.isUserWithEmailExist(email)) {
            throw new InvalidCredentialsException(String.format("User with email: %s already exist", email));
        }

        User user = userMapper.registrationDtoToUser(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userService.save(user);

        return tokenProvider.createToken(user.getEmail(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public String refreshAuthentication(String email) {
        return tokenProvider.createToken(email, List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
