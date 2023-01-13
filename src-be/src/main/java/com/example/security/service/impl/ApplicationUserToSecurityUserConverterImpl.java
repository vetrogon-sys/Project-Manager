package com.example.security.service.impl;

import com.example.entity.User;
import com.example.security.service.ApplicationUserToSecurityUserConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserToSecurityUserConverterImpl implements ApplicationUserToSecurityUserConverter {

    @Override
    public UserDetails getUserDetailsFromUser(User user) {
        return org.springframework.security.core.userdetails.User
              .withUsername(user.getEmail())
              .password(user.getPassword())
              .authorities("ROLE_USER")
              .accountExpired(false)
              .accountLocked(false)
              .credentialsExpired(false)
              .disabled(false)
              .build();
    }

}