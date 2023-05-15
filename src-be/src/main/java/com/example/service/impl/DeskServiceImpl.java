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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeskServiceImpl implements DeskService {

    public static final String DESK_WITH_ID_NOT_FOUND_MESSAGE = "Can't find Desk with id: %d";
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
        if (!deskRepository.existsById(desk.getId())) {
            throw new EntityNotFoundException(String.format(DESK_WITH_ID_NOT_FOUND_MESSAGE, desk.getId()));
        }
        return deskRepository.save(desk);
    }

    @Override
    public Desk deleteTaskInDeskById(Long deskId, Task task) {
        Desk desk = getByIdWithTasks(deskId);
        if (Boolean.FALSE.equals(desk.removeTask(task))) {
            throw new WrongArgumentException(String.format("Task %s doesn't exist in desk %s", task.getTitle(), desk.getName()));
        }

        return forceUpdate(desk);
    }

    @Override
    public Desk addTaskToDeskById(Long deskId, Task task) {
        Desk desk = getByIdWithTasks(deskId);
        desk.addTask(task);
        return forceUpdate(desk);
    }

    @Override
    public Desk removeTaskFromDeskById(Long deskId, Task task) {
        Desk desk = getByIdWithTasks(deskId);
        desk.removeTask(task);
        task.setDesk(null);
        return forceUpdate(desk);
    }

    @Override
    public Desk getById(Long deskId) {
        return deskRepository.findById(deskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(DESK_WITH_ID_NOT_FOUND_MESSAGE, deskId)));
    }

    @Override
    public Desk getByIdWithTasks(Long deskId) {
        return deskRepository.findWithTasksById(deskId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(DESK_WITH_ID_NOT_FOUND_MESSAGE, deskId)));
    }

    @Override
    public List<Desk> updateAll(List<Desk> desks) {
        List<Desk> existingDesks = desks.stream()
                .filter(desk -> deskRepository.existsById(desk.getId()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), result -> {
                    if (result.isEmpty()) throw new WrongArgumentException("None of the passed desks exist");
                    else return result;
                }));
        return deskRepository.saveAll(existingDesks);
    }

    /**
     * Method to unchecked save of entities
     *
     * @param desk - entity to update in database
     * @return - updated entity
     */
    private Desk forceUpdate(Desk desk) {
        return deskRepository.save(desk);
    }
}
