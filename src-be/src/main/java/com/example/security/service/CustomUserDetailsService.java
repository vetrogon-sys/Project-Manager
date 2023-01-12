package com.example.security.service;

import com.example.entity.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final ApplicationUserToSecurityUserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userService.getByEmail(username);

        return userConverter.getUserDetailsFromUser(dbUser);
    }

}
