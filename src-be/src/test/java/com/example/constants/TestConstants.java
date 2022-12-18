package com.example.constants;

import com.example.dto.UserDto;
import com.example.entity.Desk;
import com.example.entity.Project;
import com.example.entity.User;

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
                Arrays.asList(
                        d1, d2, d3
                )
        );
    }

    public static Project getProjectWithId(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        project.setName("Test project");
        project.setCreator(getUserEqualsToExisting());
        return project;
    }
}
