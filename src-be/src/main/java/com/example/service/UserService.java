package com.example.service;

import com.example.dto.UserDto;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getByEmail(String email);

    Page<UserDto> getAllAssignedToProjectWithId(Long projectId, Pageable pageable);

    UserDto getUserByEmailAsDto(String email);

    User save(User user);

    boolean isUserWithEmailExist(String email);

}
