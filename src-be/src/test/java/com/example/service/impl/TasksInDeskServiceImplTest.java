package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.dto.TaskDto;
import com.example.entity.Desk;
import com.example.entity.Task;
import com.example.mapper.TaskMapper;
import com.example.service.DeskService;
import com.example.service.TaskService;
import com.example.service.TasksInDeskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TasksInDeskServiceImplTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private DeskService deskService;

    private TasksInDeskService tasksInDeskService;

    @Before
    public void setUp() {
        tasksInDeskService = new TasksInDeskServiceImpl(taskService, deskService, taskMapper);
    }

    @Test
    public void createTaskInDesk() {
        Desk defaultDesk = TestConstants.getDesk();
        Task defaultTask = TestConstants.getTaskWithoutDesk();

        when(deskService.getByIdWithTasks(defaultDesk.getId()))
                .thenReturn(defaultDesk);
        when(taskMapper.taskDtoToTask(any(TaskDto.class)))
                .thenReturn(defaultTask);
        when(taskService.create(any(Task.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(deskService.addTaskToDeskById(anyLong(), any(Task.class)))
                .thenReturn(defaultDesk);
        when(taskMapper.taskToTaskDto(any(Task.class)))
                .thenReturn(TestConstants.getTaskDtoWithDesk());

        TaskDto actualTask = tasksInDeskService.createTaskInDesk(defaultDesk.getId(), TestConstants.getTaskDtoWithDesk());

        verify(deskService, times(1)).getByIdWithTasks(defaultDesk.getId());
        verify(taskMapper, times(1)).taskDtoToTask(any(TaskDto.class));
        verify(taskService, times(1)).create(any(Task.class));
        verify(deskService, times(1)).addTaskToDeskById(eq(defaultDesk.getId()), any(Task.class));
        verify(taskMapper, times(1)).taskToTaskDto(any(Task.class));
        assertEquals(TestConstants.getTaskDtoWithDesk(), actualTask);
    }

    @Test
    public void moveTaskToAnotherDesk() {
        Task defaultTask = TestConstants.getTaskWithDesk();
        Desk firstDesk = TestConstants.getDesk();
        firstDesk.setTasks(List.of(defaultTask));
        defaultTask.setDesk(firstDesk);
        Desk secondDesk = TestConstants.getDesk();
        secondDesk.setId(2L);

        long taskId = 1L;
        long currentDeskId = 1L;
        long anotherDeskId = 2L;

        when(taskService.getByIdWithDesk(taskId))
                .thenReturn(defaultTask);
        when(deskService.deleteTaskInDeskById(currentDeskId, defaultTask))
                .thenReturn(firstDesk);
        when(deskService.addTaskToDeskById(anotherDeskId, defaultTask))
                .thenReturn(secondDesk);
        when(taskService.update(any(Task.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        tasksInDeskService.moveTaskToAnotherDesk(taskId, currentDeskId, anotherDeskId);

        verify(taskService, times(1)).getByIdWithDesk(taskId);
        verify(deskService, times(1)).deleteTaskInDeskById(anyLong(), any(Task.class));
        verify(deskService, times(1)).addTaskToDeskById(anyLong(), any(Task.class));
        verify(taskService, times(1)).update(any(Task.class));

        assertEquals(secondDesk, defaultTask.getDesk());
    }

    @Test
    public void deleteTaskInDesk() {
        Task defaultTask = TestConstants.getTaskWithDesk();

        long taskId = 1L;
        long deskId = 1L;

        when(taskService.getByIdWithDesk(taskId))
                .thenReturn(defaultTask);

        tasksInDeskService.deleteTaskInDesk(deskId, taskId);

        verify(taskService, times(1)).getByIdWithDesk(taskId);
        verify(deskService, times(1)).deleteTaskInDeskById(deskId, defaultTask);
        verify(taskService, times(1)).deleteById(taskId);
    }

    @Test
    public void getTasksFromDesk() {
        Desk desk = TestConstants.getDesk();
        List<Task> tasksList = TestConstants.getTasksList();
        desk.setTasks(tasksList);
        PageImpl<Task> taskPage = new PageImpl<>(tasksList);

        long deskId = 1L;

        when(deskService.getById(deskId))
                .thenReturn(TestConstants.getDesk());
        when(taskService.getAllFromDesk(any(Desk.class), any(Pageable.class)))
                .thenReturn(taskPage);
        when(taskMapper.taskToTaskDto(any(Task.class)))
                .thenReturn(TestConstants.getTaskDtoWithDesk());

        Page<TaskDto> actualTasks = tasksInDeskService.getTasksFromDesk(deskId, Pageable.ofSize(5));

        verify(deskService, times(1)).getById(deskId);
        verify(taskService, times(1)).getAllFromDesk(any(Desk.class), any(Pageable.class));
        verify(taskMapper, times(2)).taskToTaskDto(any(Task.class));

        assertEquals(List.of(TestConstants.getTaskDtoWithDesk(), TestConstants.getTaskDtoWithDesk()),
                actualTasks.getContent());
    }

    @Test
    public void updateTask() {
        TaskDto defaultTask = TestConstants.getTaskDtoWithoutDesk();

        when(taskMapper.taskDtoToTask(any(TaskDto.class)))
                .thenReturn(TestConstants.getTaskWithoutDesk());
        when(taskService.update(any(Task.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        tasksInDeskService.updateTask(TestConstants.getTaskWithoutDesk().getId(), defaultTask);

        verify(taskMapper, times(1)).taskDtoToTask(any(TaskDto.class));
        verify(taskService, times(1)).update(any(Task.class));
    }
}