package com.example.service.impl;

import com.example.dto.AuthorizationDto;
import com.example.dto.JWTTokenDto;
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

    public static final SimpleGrantedAuthority DEFAULT_USER_ROLE_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JWTTokenDto signIn(AuthorizationDto authorizationDto) {
        try {
            String email = authorizationDto.getEmail();
            Authentication authentication
                  = new UsernamePasswordAuthenticationToken(
                  email,
                  authorizationDto.getPassword()
            );
            authenticationManager.authenticate(authentication);
            return new JWTTokenDto(tokenProvider.createToken(email, List.of(DEFAULT_USER_ROLE_AUTHORITY)));
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Authentication exception. Check passed credentials");
        }
    }

    @Override
    public JWTTokenDto signUp(RegistrationDto registrationDto) {
        String email = registrationDto.getEmail();
        if (userService.isUserWithEmailExist(email)) {
            throw new InvalidCredentialsException(String.format("User with email: %s already exist", email));
        }

        User user = userMapper.registrationDtoToUser(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userService.save(user);

        return new JWTTokenDto(tokenProvider.createToken(user.getEmail(), List.of(DEFAULT_USER_ROLE_AUTHORITY)));
    }

    @Override
    public JWTTokenDto refreshAuthentication(String email) {
        return new JWTTokenDto(tokenProvider.createToken(email, List.of(DEFAULT_USER_ROLE_AUTHORITY)));
    }

}
