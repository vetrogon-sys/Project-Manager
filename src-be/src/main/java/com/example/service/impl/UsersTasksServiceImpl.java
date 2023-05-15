package com.example.service.impl;

import com.example.entity.User;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.UsersTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersTasksServiceImpl implements UsersTasksService {

    private final UserService userService;
    private final TaskService taskService;

    @Override
    public void assignUserWithIdToTaskWithId(Long userId, Long taskId) {
        User user = userService.getById(userId);

        taskService.assignUserToTaskWithId(taskId, user);
    }

}
