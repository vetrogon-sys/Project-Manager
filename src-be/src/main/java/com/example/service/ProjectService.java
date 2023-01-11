package com.example.service;

import com.example.dto.ProjectDto;
import com.example.entity.Project;

public interface ProjectService {

    Project getById(Long projectId);

    ProjectDto getByIdAsDto(Long projectId);

    ProjectDto create(Project project);

    ProjectDto update(Project project);

    void deleteById(Long projectId);

    boolean existById(Long projectId);

}
