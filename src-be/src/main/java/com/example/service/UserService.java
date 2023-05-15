package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UsersIdsList;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User getByEmail(String email);

    User getById(Long id);

    List<User> getAllByIds(List<Long> ids);

    Page<UserDto> getAllExclusionsIds(UsersIdsList exclusions, Pageable pageable);

    Page<UserDto> getAllAssignedToProjectWithId(Long projectId, Pageable pageable);

    UserDto getUserByEmailAsDto(String email);

    User save(User user);

    boolean isUserWithEmailExist(String email);

    UserDto getAssignedToTaskWithId(Long taskId);

    boolean isAssignedToTaskWithIdExist(Long taskId);
}
