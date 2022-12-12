package com.example.service.impl;

import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.exeptions.WrongArgumentException;
import com.example.repository.DeskRepository;
import com.example.service.DeskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeskServiceImpl implements DeskService {

    private final DeskRepository deskRepository;

    @Override
    public List<Desk> getAllByProjectIdEquals(Long projectId) {
        return deskRepository.findAllByProjectIdEquals(projectId);
    }

    @Override
    public Desk create(Desk desk) {
        return deskRepository.save(desk);
    }

    @Override
    public void deleteById(Long id) {
        deskRepository.deleteById(id);
    }

    @Override
    public Desk update(Desk desk) {
        return deskRepository.save(desk);
    }

    @Override
    public Desk deleteTaskInDeskById(Long deskId, Task task) {
        Desk desk = getByIdWithTasks(deskId);
        if (!desk.removeTask(task)) {
            throw new WrongArgumentException(String.format("Task %s doesn't exist in desk %s", task.getTitle(), desk.getName()));
        }

        return update(desk);
    }

    @Override
    public Desk addTaskToDeskById(Long deskId, Task task) {
        Desk desk = getByIdWithTasks(deskId);
        desk.addTask(task);
        return update(desk);
    }

    @Override
    public Desk getById(Long deskId) {
        return deskRepository.findById(deskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find Desk with id: %d", deskId)));
    }

    @Override
    public Desk getByIdWithTasks(Long deskId) {
        return deskRepository.findWithTasksById(deskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find Desk with id: %d", deskId)));
    }

    @Override
    public List<Desk> updateAll(List<Desk> desks) {
        return deskRepository.saveAll(desks);
    }
}
