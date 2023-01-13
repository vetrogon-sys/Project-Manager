package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.User;

public interface UserService {

    User getByEmail(String email);

    UserDto getUserByEmailAsDto(String email);

    User save(User user);

    boolean isUserWithEmailExist(String email);

}
