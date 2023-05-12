package com.example.service.impl;

import com.example.entity.User;
import com.example.service.ProjectService;
import com.example.service.UserService;
import com.example.service.UsersInProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersInProjectServiceImpl implements UsersInProjectService {
    private final UserService userService;
    private final ProjectService projectService;

    @Override
    public void assignUsersByIdsToProjectById(Long projectId, List<Long> usersIds) {
        List<User> usersToAssign = userService.getAllByIds(usersIds);

        projectService.assignUsersToProjectById(projectId, usersToAssign);
    }
}
