package com.example.service.impl;

import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    @Before
    public void setUp() {
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    public void create() {
        Desk desk = new Desk();
        desk.setId(1L);
        desk.setName("Tested desk");

        Task task = new Task();
        task.setTitle("Test tasks");
        task.setDesk(desk);

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        Task actualTask = taskService.create(task);

        verify(taskRepository, times(1)).save(task);
        verifyNoMoreInteractions(taskRepository);
        assertEquals(task, actualTask);
    }

    @Test
    public void getByIdWithDeskIfTaskIsExist() {
        Desk desk = new Desk();
        long taskId = 1L;
        desk.setId(taskId);
        desk.setName("Tested desk");

        Task task = new Task();
        task.setTitle("Test tasks");
        task.setDesk(desk);

        when(taskRepository.findWithDeskById(taskId))
                .thenReturn(Optional.of(task));

        Task actualTask = taskService.getByIdWithDesk(taskId);

        verify(taskRepository, times(1)).findWithDeskById(taskId);
        verifyNoMoreInteractions(taskRepository);
        assertEquals(task, actualTask);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdWithDeskIfTaskDoesNotExist() {
        long taskId = 1L;

        when(taskRepository.findWithDeskById(taskId))
                .thenReturn(Optional.empty());

        taskService.getByIdWithDesk(taskId);

        verify(taskRepository, times(1)).findWithDeskById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void deleteByIdIfTaskWithIdExist() {
        long taskId = 1L;

        when(taskRepository.existsById(taskId))
                .thenReturn(true);

        taskService.deleteById(taskId);

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteByIdIfTaskWithIdDoesNotExist() {
        long taskId = 1L;

        when(taskRepository.existsById(taskId))
                .thenReturn(false);

        taskService.deleteById(taskId);

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(0)).deleteById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void updateIfTaskIsExist() {
        long taskId = 1L;

        when(taskRepository.existsById(taskId))
                .thenReturn(true);

        taskService.deleteById(taskId);

        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void getAllFromDesk() {
        Desk desk = new Desk();
        long taskId = 1L;
        desk.setId(taskId);
        desk.setName("Tested desk");

        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("Test tasks");
        t1.setDesk(desk);

        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("Test tasks");
        t2.setDesk(desk);

        ArrayList<Task> tasks = new ArrayList<>(Arrays.asList(t1, t2));
        desk.setTasks(tasks);

        Pageable pageable = Pageable.ofSize(2);
        Page<Task> taskPage = new PageImpl(tasks, pageable, 2);
        when(taskRepository.findAllByDesk(desk, pageable))
                .thenReturn(taskPage);

        Page<Task> actualTasks = taskService.getAllFromDesk(desk, pageable);

        verify(taskRepository, times(1)).findAllByDesk(desk, pageable);
        verifyNoMoreInteractions(taskRepository);
        assertEquals(taskPage, actualTasks);
    }

}