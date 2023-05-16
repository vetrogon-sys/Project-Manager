package com.example.service.impl;

import com.example.dto.ProjectDto;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.entity.security.SecurityOpportunity;
import com.example.mapper.ProjectMapper;
import com.example.service.AuthorityService;
import com.example.service.ProjectService;
import com.example.service.SecurityOpportunityService;
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

    private final AuthorityService authorityService;
    private final SecurityOpportunityService securityOpportunityService;

    @Override
    public void assignUsersByIdsToProjectById(Long projectId, List<Long> usersIds) {
        List<User> usersToAssign = userService.getAllByIds(usersIds);

        Project project = projectService.assignUsersToProjectById(projectId, usersToAssign);

        List<SecurityOpportunity> basicOpportunities = securityOpportunityService.getBasicOpportunities();

        usersToAssign.forEach(
              user -> authorityService.createAuthority(project, user, basicOpportunities)
        );
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto, String currentUserEmail) {
        User currentUser = userService.getByEmail(currentUserEmail);
        Project project = projectMapper.projectDtoToProject(projectDto);
        project.setCreator(currentUser);
        Project savedProject = projectService.create(project);

        authorityService
              .createAuthority(savedProject, currentUser, securityOpportunityService.getAll());

        return projectMapper.projectToProjectDto(savedProject);
    }

}
