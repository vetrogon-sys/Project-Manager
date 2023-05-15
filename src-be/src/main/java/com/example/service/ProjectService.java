package com.example.service;

import com.example.dto.ProjectDto;
import com.example.entity.Project;
import com.example.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {

    Project getById(Long projectId);

    ProjectDto getByIdAsDto(Long projectId);

    ProjectDto create(Project project);

    ProjectDto update(Project project);

    ProjectDto update(Long projectId, ProjectDto projectDto);

    List<ProjectDto> getAllWhereUserWithEmailIsCreator(String email, Pageable pageable);

    List<ProjectDto> getAllWhereUserWithEmailIsAssigned(String email, Pageable pageable);

    void deleteById(Long projectId);

    boolean existById(Long projectId);

    void removeAssignedUsersWithIdsFromProjectWithId(List<Long> idsList, Long projectId);

    void assignUsersToProjectById(Long projectId, List<User> users);
}
