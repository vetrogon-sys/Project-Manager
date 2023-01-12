package com.example.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface TokenProvider {

    String createToken(String username, List<GrantedAuthority> authorities);

    Authentication getAuthenticationFromToken(String token);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);

}
