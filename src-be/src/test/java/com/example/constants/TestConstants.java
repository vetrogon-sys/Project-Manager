package com.example.constants;

import com.example.dto.DeskDto;
import com.example.dto.TaskDto;
import com.example.dto.UserDto;
import com.example.entity.Desk;
import com.example.entity.Project;
import com.example.entity.Task;
import com.example.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConstants {
    public static final String EXISTING_EMAIL = "user_test@gmail.com";
    public static final String NON_EXISTED_EMAIL = "non-existed-email@gmail.com";

    public static User getUserEqualsToExisting() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setFirstName("Adam");
        user.setLastName("Green");
        return user;
    }

    public static UserDto getUserDtoEqualsToExisting() {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setFirstName("Adam");
        user.setLastName("Green");
        return user;
    }

    public static List<Desk> getDeskListWithProjectcId(Long projectId) {
        Project project = getProjectWithId(projectId);

        Desk d1 = new Desk();
        d1.setId(1L);
        d1.setName("Desk 1");
        d1.setProject(project);
        Desk d2 = new Desk();
        d2.setId(2L);
        d2.setName("Desk 2");
        d2.setProject(project);
        Desk d3 = new Desk();
        d3.setId(3L);
        d3.setName("Desk 3");
        d3.setProject(project);

        return new ArrayList<>(
                List.of(
                        d1, d2, d3
                )
        );
    }

    public static List<DeskDto> getDeskDtoListWith() {
        DeskDto d1 = new DeskDto();
        d1.setId(1L);
        d1.setName("Desk 1");
        DeskDto d2 = new DeskDto();
        d2.setId(2L);
        d2.setName("Desk 2");
        DeskDto d3 = new DeskDto();
        d3.setId(3L);
        d3.setName("Desk 3");

        return new ArrayList<>(
              List.of(
                    d1, d2, d3
              )
        );
    }

    public static DeskDto getDeskDto() {
        DeskDto desk = new DeskDto();
        desk.setId(1L);
        desk.setName("Testing desk");
        return desk;
    }

    public static TaskDto getTaskDtoWithDesk() {
        TaskDto task = new TaskDto();
        task.setId(1L);
        task.setTitle("Testing task");
        task.setCreationDate(LocalDate.of(2010, 11, 12));
        task.setDesk(getDeskDto());
        return task;
    }

    public static TaskDto getTaskDtoWithoutDesk() {
        TaskDto task = new TaskDto();
        task.setId(1L);
        task.setTitle("Testing task");
        task.setCreationDate(LocalDate.of(2010, 11, 12));
        return task;
    }

    public static List<Task> getTasksList() {
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("Testing t1");
        t1.setAssignedUser(getUserEqualsToExisting());
        t1.setCreationDate(LocalDate.of(2010, 11, 12));
        t1.setDesk(getDesk());
        Task t2 = new Task();
        t2.setId(2L);
        t2.setTitle("Testing t2");
        t2.setAssignedUser(getUserEqualsToExisting());
        t2.setCreationDate(LocalDate.of(2010, 11, 12));
        t2.setDesk(getDesk());

        return new ArrayList<>(
                List.of(t1, t2)
        );
    }

    public static Task getTaskWithDesk() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Testing task");
        task.setAssignedUser(getUserEqualsToExisting());
        task.setCreationDate(LocalDate.of(2010, 11, 12));
        task.setDesk(getDesk());
        return task;
    }

    public static Task getTaskWithoutDesk() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Testing task");
        task.setAssignedUser(getUserEqualsToExisting());
        task.setCreationDate(LocalDate.of(2010, 11, 12));
        return task;
    }

    public static Desk getDesk() {
        Desk desk = new Desk();
        desk.setId(1L);
        desk.setName("Testing desk");
        return desk;
    }

    public static Project getProjectWithId(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        project.setName("Test project");
        project.setCreator(getUserEqualsToExisting());
        return project;
    }

    public static Project getProjectWithoutId() {
        Project project = new Project();
        project.setName("Test project");
        project.setCreator(getUserEqualsToExisting());
        return project;
    }

}
