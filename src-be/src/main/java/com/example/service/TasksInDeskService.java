package com.example.service;

import com.example.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TasksInDeskService {

    TaskDto createTaskInDesk(Long deskId, TaskDto taskDto);

    void moveTaskToAnotherDesk(Long taskId, Long currentDeskId, Long anotherDeskId);

    void deleteTaskInDesk(Long deskId, Long taskId);

    void deleteAllTasksFromDesk(Long deskId);

    Page<TaskDto> getTasksFromDesk(Long deskId, Pageable pageable);

    void updateTask(Long taskId, TaskDto taskDto);

}
