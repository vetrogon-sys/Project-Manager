package com.example.security.service;

import com.example.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface ApplicationUserToSecurityUserConverter {

    UserDetails getUserDetailsFromUser(User user);

}
