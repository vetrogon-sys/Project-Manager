package com.example.service.impl;

import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getByIdWithDesk(Long taskId) {
        return taskRepository.findWithDeskById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find Task with id: %d", taskId)));
    }

    @Override
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Task with id: %d doesn't exist", id));
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Task update(Task task) {
        if (!taskRepository.existsById(task.getId())) {
            throw new EntityNotFoundException(String.format("Task with id: %d doesn't exist", task.getId()));
        }
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getAllFromDesk(Desk desk, Pageable pageable) {
        return taskRepository.findAllByDesk(desk, pageable);
    }

}
