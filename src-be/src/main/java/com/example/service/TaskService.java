package com.example.service;

import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task getByIdWithDesk(Long taskId);

    Task getById(Long taskId);

    void deleteById(Long id);

    Task update(Task task);

    Page<Task> getAllFromDesk(Desk desk, Pageable pageable);

    Page<Task> getAllFromDeskById(Long deskId, Pageable pageable);

    void assignUserToTaskWithId(Long taskId, User user);

    void unassignUserFromTaskWithId(Long taskId);

    void deleteAllIn(List<Task> tasks);
}
