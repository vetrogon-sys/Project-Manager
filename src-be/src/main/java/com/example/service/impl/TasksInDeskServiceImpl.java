package com.example.service.impl;

import com.example.dto.TaskDto;
import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.mapper.TaskMapper;
import com.example.service.DeskService;
import com.example.service.TaskService;
import com.example.service.TasksInDeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksInDeskServiceImpl implements TasksInDeskService {

    private final TaskService taskService;
    private final DeskService deskService;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto createTaskInDesk(Long deskId, TaskDto taskDto) {
        Desk desk = deskService.getById(deskId);

        Task task = taskMapper.taskDtoToTask(taskDto);
        task.setDesk(desk);
        Task createdTask = taskService.create(task);

        return taskMapper.taskToTaskDto(createdTask);
    }

    @Override
    @Transactional
    public void moveTaskToAnotherDesk(Long taskId, Long currentDeskId, Long anotherDeskId) {
        Task task = taskService.getByIdWithDesk(taskId);
        Desk anotherDesk = deskService.getById(anotherDeskId);

        task.setDesk(anotherDesk);
        taskService.update(task);
    }

    @Override
    public void deleteTaskInDesk(Long deskId, Long taskId) {
        Task task = taskService.getById(taskId);
        deskService.removeTaskFromDeskById(deskId, task);
        taskService.deleteById(taskId);
    }

    @Override
    public void deleteAllTasksFromDesk(Long deskId) {
        Desk desk = deskService.getByIdWithTasks(deskId);
        List<Task> tasks = desk.getTasks();
        desk.removeAllTasks();
        taskService.deleteAllIn(tasks);
    }

    @Override
    public Page<TaskDto> getTasksFromDesk(Long deskId, Pageable pageable) {
        return taskService.getAllFromDeskById(deskId, pageable)
                .map(taskMapper::taskToTaskDto);
    }

    @Override
    public void updateTask(Long taskId, TaskDto taskDto) {
        Task oldTask = taskService.getById(taskId);
        oldTask.fillRequiredFields(taskDto);

        taskService.update(oldTask);
    }

}
