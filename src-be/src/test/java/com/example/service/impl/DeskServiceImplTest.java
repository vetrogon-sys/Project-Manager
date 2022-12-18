package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.exeptions.WrongArgumentException;
import com.example.repository.DeskRepository;
import com.example.service.DeskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeskServiceImplTest {

    @Mock
    private DeskRepository deskRepository;

    private DeskService deskService;

    @Before
    public void setUp() {
        deskService = new DeskServiceImpl(deskRepository);
    }

    @Test
    public void getAllByProjectIdEqualsIfDesksWithProjectIdExist() {
        long projectId = 1L;
        List<Desk> expectedDesks = TestConstants.getDeskListWithProjectcId(projectId);

        when(deskRepository.findAllByProjectIdEquals(projectId))
                .thenReturn(expectedDesks);

        List<Desk> actualDesks = deskService.getAllByProjectIdEquals(projectId);

        verify(deskRepository, times(1)).findAllByProjectIdEquals(projectId);
        assertEquals(expectedDesks, actualDesks);
    }

    @Test
    public void getAllByProjectIdEqualsIfDesksWithProjectIdDoesNotExist() {
        long projectId = 1L;

        when(deskRepository.findAllByProjectIdEquals(projectId))
                .thenReturn(new ArrayList<>());

        List<Desk> actualDesks = deskService.getAllByProjectIdEquals(projectId);

        verify(deskRepository, times(1)).findAllByProjectIdEquals(projectId);
        assertTrue(actualDesks.isEmpty());
    }

    @Test
    public void create() {
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk 1");
        desk.setId(1L);

        when(deskRepository.save(any(Desk.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Desk actualDesk = deskService.create(desk);

        verify(deskRepository, times(1)).save(desk);
        assertEquals(desk, actualDesk);
    }

    @Test
    public void deleteById() {
        long deskId = 1L;

        doNothing().when(deskRepository)
                .deleteById(deskId);

        deskService.deleteById(deskId);

        verify(deskRepository, times(1)).deleteById(deskId);
    }

    @Test
    public void updateIfDeskExist() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk updated name");
        desk.setId(deskId);

        when(deskRepository.existsById(deskId))
                .thenReturn(true);
        when(deskRepository.save(any(Desk.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Desk actualDesk = deskService.update(desk);

        verify(deskRepository, times(1)).existsById(deskId);
        verify(deskRepository, times(1)).save(desk);
        assertEquals(desk, actualDesk);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateIfDeskDoesNotExist() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk updated name");
        desk.setId(deskId);

        when(deskRepository.existsById(deskId))
                .thenReturn(false);

        deskService.update(desk);

        verify(deskRepository, times(1)).existsById(deskId);
        verify(deskRepository, times(0)).save(any(Desk.class));
    }

    @Test
    public void deleteTaskInDeskByIdIfSameTaskExistInDesk() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk updated name");
        desk.setId(deskId);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");

        Task taskToRemove = new Task();
        taskToRemove.setId(2L);
        taskToRemove.setTitle("Test task - 2");

        ArrayList<Task> tasks = new ArrayList<>(Arrays.asList(task, taskToRemove));
        desk.setTasks(tasks);

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.of(desk));

        when(deskRepository.save(any(Desk.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Desk actualDesk = deskService.deleteTaskInDeskById(deskId, taskToRemove);

        desk.getTasks().remove(taskToRemove);
        verify(deskRepository, times(1)).findWithTasksById(deskId);
        verify(deskRepository, times(1)).save(desk);
        assertEquals(desk, actualDesk);
    }

    @Test(expected = WrongArgumentException.class)
    public void deleteTaskInDeskByIdIfSameTaskDoesNotExistInDesk() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk updated name");
        desk.setId(deskId);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");

        Task taskToRemove = new Task();
        taskToRemove.setId(2L);
        taskToRemove.setTitle("Test task - 2");

        ArrayList<Task> tasks = new ArrayList<>(Collections.singletonList(task));
        desk.setTasks(tasks);

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.of(desk));

        deskService.deleteTaskInDeskById(deskId, taskToRemove);

        verify(deskRepository, times(1)).findWithTasksById(deskId);
        verify(deskRepository, times(0)).save(any(Desk.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteTaskInDeskByIfDeskDoesNotExist() {
        long deskId = 1L;

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.empty());

        deskService.deleteTaskInDeskById(deskId, new Task());

        verify(deskRepository, times(1)).findWithTasksById(deskId);
        verify(deskRepository, times(0)).save(any(Desk.class));
    }

    @Test
    public void addTaskToDeskByIdIfDeskExist() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk tester");
        desk.setId(deskId);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");

        Task taskToAdd = new Task();
        taskToAdd.setId(2L);
        taskToAdd.setTitle("Test task - 2");

        ArrayList<Task> tasks = new ArrayList<>(Collections.singletonList(task));
        desk.setTasks(tasks);

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.of(desk));
        when(deskRepository.save(any(Desk.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Desk actualDesk = deskService.addTaskToDeskById(deskId, taskToAdd);

        tasks.add(taskToAdd);
        desk.setTasks(tasks);

        verify(deskRepository, times(1)).findWithTasksById(deskId);
        verify(deskRepository, times(1)).save(desk);
        assertEquals(desk, actualDesk);
    }

    @Test(expected = EntityNotFoundException.class)
    public void addTaskToDeskByIdIfDeskDoesNotExist() {
        long deskId = 1L;

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.empty());

        deskService.addTaskToDeskById(deskId, new Task());

        verify(deskRepository, times(1)).findWithTasksById(deskId);
        verify(deskRepository, times(0)).save(any(Desk.class));
    }

    @Test
    public void getByIdIfDeskWithIdIsExist() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk");
        desk.setId(deskId);

        when(deskRepository.findById(deskId))
                .thenReturn(Optional.of(desk));

        Desk actualDesk = deskService.getById(deskId);

        verify(deskRepository, times(1)).findById(deskId);
        assertEquals(desk, actualDesk);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdIfDeskWithIdDoesNotExist() {
        long deskId = 1L;

        when(deskRepository.findById(deskId))
                .thenReturn(Optional.empty());

        deskService.getById(deskId);

        verify(deskRepository, times(1)).findWithTasksById(deskId);
    }

    @Test
    public void getByIdWithTasksIfDeskExist() {
        long deskId = 1L;
        Desk desk = new Desk();
        desk.setProject(TestConstants.getProjectWithId(1L));
        desk.setName("Desk");
        desk.setId(deskId);

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test task");

        ArrayList<Task> tasks = new ArrayList<>(Collections.singletonList(task));
        desk.setTasks(tasks);

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.of(desk));

        Desk actualDesk = deskService.getByIdWithTasks(deskId);

        verify(deskRepository, times(1)).findWithTasksById(deskId);
        assertEquals(desk, actualDesk);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWithTasksIfDeskDoesNotExist() {
        long deskId = 1L;

        when(deskRepository.findWithTasksById(deskId))
                .thenReturn(Optional.empty());

        deskService.getByIdWithTasks(deskId);

        verify(deskRepository, times(1)).findWithTasksById(deskId);
    }

    @Test
    public void updateAllIfAllExist() {
        Desk d1 = new Desk();
        d1.setProject(TestConstants.getProjectWithId(1L));
        d1.setName("Desk");
        d1.setId(1L);

        Desk d2 = new Desk();
        d2.setProject(TestConstants.getProjectWithId(1L));
        d2.setName("Desk-2");
        d2.setId(2L);

        ArrayList<Desk> desks = new ArrayList<>(Arrays.asList(d1, d2));
        when(deskRepository.existsById(1L))
                .thenReturn(true);
        when(deskRepository.existsById(2L))
                .thenReturn(true);
        when(deskRepository.saveAll(anyCollection()))
                .thenAnswer(i -> i.getArguments()[0]);

        List<Desk> actualDesks = deskService.updateAll(desks);
        verify(deskRepository, times(2)).existsById(anyLong());
        verify(deskRepository, times(1)).saveAll(desks);
        assertEquals(desks, actualDesks);
    }

    @Test
    public void updateAllIfNotAllExist() {
        Desk d1 = new Desk();
        d1.setProject(TestConstants.getProjectWithId(1L));
        d1.setName("Desk");
        d1.setId(1L);

        Desk nonExistingDesk = new Desk();
        nonExistingDesk.setProject(TestConstants.getProjectWithId(1L));
        nonExistingDesk.setName("Desk-2");
        nonExistingDesk.setId(2L);

        ArrayList<Desk> desks = new ArrayList<>(Arrays.asList(d1, nonExistingDesk));
        when(deskRepository.existsById(1L))
                .thenReturn(true);
        when(deskRepository.existsById(2L))
                .thenReturn(false);

        when(deskRepository.saveAll(anyCollection()))
                .thenAnswer(i -> i.getArguments()[0]);

        List<Desk> actualDesks = deskService.updateAll(desks);

        desks.remove(nonExistingDesk);
        verify(deskRepository, times(2)).existsById(anyLong());
        verify(deskRepository, times(1)).saveAll(desks);
        assertEquals(desks, actualDesks);
    }

}