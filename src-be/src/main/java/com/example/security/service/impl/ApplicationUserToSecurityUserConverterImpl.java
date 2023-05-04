package com.example.security.service.impl;

import com.example.entity.User;
import com.example.security.service.ApplicationUserToSecurityUserConverter;
import com.example.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserToSecurityUserConverterImpl implements ApplicationUserToSecurityUserConverter {

    private final AuthorityService authorityService;
    @Override
    public UserDetails getUserDetailsFromUser(User user) {
        return org.springframework.security.core.userdetails.User
              .withUsername(user.getEmail())
              .password(user.getPassword())
              .authorities(authorityService.getAllByUserEmailAsGrantedAuthorities(user.getEmail()))
              .accountExpired(false)
              .accountLocked(false)
              .credentialsExpired(false)
              .disabled(false)
              .build();
    }

}