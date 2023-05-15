package com.example.service;

import com.example.dto.ProjectDto;

import java.util.List;

public interface UsersInProjectService {

    void assignUsersByIdsToProjectById(Long projectId, List<Long> usersIds);

    ProjectDto createProject(ProjectDto projectDto, String currentUserEmail);
}
