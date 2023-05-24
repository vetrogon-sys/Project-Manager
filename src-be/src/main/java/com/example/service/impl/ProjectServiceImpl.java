package com.example.service.impl;

import com.example.dto.ProjectDto;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.mapper.ProjectMapper;
import com.example.repository.ProjectRepository;
import com.example.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project getById(Long projectId) {
        return projectRepository.findById(projectId)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find Project with id: %d", projectId)));
    }

    @Override
    public ProjectDto getByIdAsDto(Long projectId) {
        return projectMapper.projectToProjectDto(getById(projectId));
    }

    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public ProjectDto update(Project project) {
        if (!projectRepository.existsById(project.getId())) {
            throw new EntityNotFoundException(String.format("Can't update Project with id: %d", project.getId()));
        }
        return projectMapper.projectToProjectDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto update(Long projectId, ProjectDto projectDto) {
        Project project = getById(projectId);
        project.setDescription(projectDto.getDescription());
        return update(project);
    }

    @Override
    public List<ProjectDto> getAllWhereUserWithEmailIsCreator(String email, Pageable pageable) {
        return projectRepository.findAllByCreatorEmailEquals(email, pageable).stream()
              .map(projectMapper::projectToProjectDto)
              .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> getAllWhereUserWithEmailIsAssigned(String email, Pageable pageable) {
        return projectRepository.findAllByAssignedUsersEmailEquals(email, pageable).stream()
              .map(projectMapper::projectToProjectDto)
              .collect(Collectors.toList());
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

    @Override
    public void removeAssignedUsersWithIdsFromProjectWithId(List<Long> idsList, Long projectId) {
        Project project = getProjectWithAssignedUsersById(projectId);

        project.removeAssignedUsersWithIds(idsList);

        projectRepository.save(project);
    }

    @Override
    public void assignUsersToProjectById(Long projectId, List<User> users) {
        Project project = getProjectWithAssignedUsersById(projectId);
        project.addAssignUsers(users);
        update(project);
    }

    private Project getProjectWithAssignedUsersById(Long projectId) {
        return projectRepository.findWithAssignedUsersById(projectId)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find project with id: %d", projectId)));
    }
}
