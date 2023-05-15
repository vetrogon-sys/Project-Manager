package com.example.service.impl;

import com.example.dto.ProjectDto;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.mapper.ProjectMapper;
import com.example.service.ProjectService;
import com.example.service.UserService;
import com.example.service.UsersInProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersInProjectServiceImpl implements UsersInProjectService {
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Override
    public void assignUsersByIdsToProjectById(Long projectId, List<Long> usersIds) {
        List<User> usersToAssign = userService.getAllByIds(usersIds);

        projectService.assignUsersToProjectById(projectId, usersToAssign);
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto, String currentUserEmail) {
        User currentUser = userService.getByEmail(currentUserEmail);
        Project project = projectMapper.projectDtoToProject(projectDto);
        project.setCreator(currentUser);
        Project savedProject = projectService.create(project);
        return projectMapper.projectToProjectDto(savedProject);
    }

}
