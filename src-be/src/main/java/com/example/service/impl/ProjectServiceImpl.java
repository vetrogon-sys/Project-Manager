package com.example.service.impl;

import com.example.entity.Project;
import com.example.repository.ProjectRepository;
import com.example.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project getById(Long projectId) {
        return projectRepository.findById(projectId)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find Project with id: %d", projectId)));
    }

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        if (!projectRepository.existsById(project.getId())) {
            throw new EntityNotFoundException(String.format("Can't update Project with id: %d", project.getId()));
        }
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException(String.format("Can't delete Project with id: %d", projectId));
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public boolean existById(Long projectId) {
        return projectRepository.existsById(projectId);
    }
}
