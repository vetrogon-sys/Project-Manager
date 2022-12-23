package com.example.service;

import com.example.entity.Desk;
import com.example.entity.Task;

import java.util.List;

public interface DeskService {

    List<Desk> getAllByProjectIdEquals(Long projectId);

    Desk create(Desk desk);

    void deleteById(Long id);

    Desk update(Desk desk);

    Desk deleteTaskInDeskById(Long deskId, Task task);

    Desk addTaskToDeskById(Long deskId, Task task);

    Desk getById(Long deskId);

    Desk getByIdWithTasks(Long deskId);

    List<Desk> updateAll(List<Desk> desks);

}
