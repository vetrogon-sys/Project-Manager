package com.example.service.impl;

import com.example.dto.UserDto;
import com.example.dto.UsersIdsList;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find user with emil: %s", email)));
    }

    @Override
    public List<User> getAllByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public Page<UserDto> getAllExclusionsIds(UsersIdsList exclusions, Pageable pageable) {
        return userRepository.findByIdNotIn(exclusions.getUserIds(), pageable)
              .map(userMapper::userToUserDto);
    }

    @Override
    public Page<UserDto> getAllAssignedToProjectWithId(Long projectId, Pageable pageable) {
        return userRepository.findAllByAssignedProjectsIdEquals(projectId, pageable)
              .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto getUserByEmailAsDto(String email) {
        return userMapper.userToUserDto(getByEmail(email));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

}
