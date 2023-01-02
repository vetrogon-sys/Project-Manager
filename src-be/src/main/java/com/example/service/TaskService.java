package com.example.service;

import com.example.entity.Desk;
import com.example.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Task create(Task task);

    Task getByIdWithDesk(Long taskId);

    void deleteById(Long id);

    Task update(Task task);

    Page<Task> getAllFromDesk(Desk desk, Pageable pageable);

    Page<Task> getAllFromDeskById(Long deskId, Pageable pageable);

}
