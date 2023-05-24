package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.dto.ProjectDto;
import com.example.entity.Project;
import com.example.mapper.ProjectMapper;
import com.example.repository.ProjectRepository;
import com.example.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    private ProjectService projectService;

    @Before
    public void setUp() {
        projectService = new ProjectServiceImpl(projectRepository, projectMapper);
    }

    @Test
    public void getByIdIfProjectExist() {
        long projectId = 1L;
        Project project = TestConstants.getProjectWithId(projectId);

        when(projectRepository.findById(projectId))
              .thenReturn(Optional.of(project));

        Project actualProject = projectService.getById(projectId);

        verify(projectRepository, times(1)).findById(projectId);
        assertEquals(project, actualProject);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByIdIfProjectDoesNotExist() {
        when(projectRepository.findById(anyLong()))
              .thenReturn(Optional.empty());

        projectService.getById(1L);

        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getByIdAsDtoIfProjectExist() {
        long projectId = 1L;
        Project project = TestConstants.getProjectWithId(projectId);
        ProjectDto projectDto = TestConstants.getProjectDtoWithoutId();

        when(projectRepository.findById(projectId))
              .thenReturn(Optional.of(project));
        when(projectMapper.projectToProjectDto(project))
              .thenReturn(projectDto);

        ProjectDto actualProject = projectService.getByIdAsDto(projectId);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectMapper, times(1)).projectToProjectDto(project);
        assertEquals(projectDto, actualProject);
    }


    @Test
    public void create() {
        Project projectWithoutId = TestConstants.getProjectWithoutId();

        when(projectRepository.save(projectWithoutId))
              .thenAnswer(i -> i.getArguments()[0]);

        Project actualProject = projectService.create(projectWithoutId);

        verify(projectRepository, times(1)).save(eq(projectWithoutId));
        assertEquals(projectWithoutId, actualProject);
    }

    @Test
    public void updateIfProjectExist() {
        long projectId = 1L;
        Project project = TestConstants.getProjectWithId(projectId);
        ProjectDto projectDto = TestConstants.getProjectDtoWithoutId();

        when(projectRepository.existsById(projectId))
              .thenReturn(true);
        when(projectRepository.save(project))
              .thenAnswer(i -> i.getArguments()[0]);
        when(projectMapper.projectToProjectDto(any(Project.class)))
              .thenReturn(projectDto);

        ProjectDto actualProject = projectService.update(project);

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).save(project);
        assertEquals(projectDto, actualProject);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateIfProjectDoesNotExist() {
        long projectId = 1L;
        Project project = TestConstants.getProjectWithId(projectId);

        when(projectRepository.existsById(projectId))
              .thenReturn(false);

        projectService.update(project);

        verify(projectRepository, times(1)).existsById(projectId);
        verifyNoInteractions(projectRepository.save(any(Project.class)));
    }

    @Test
    public void deleteByIdIfProjectExist() {
        long projectId = 1L;

        when(projectRepository.existsById(projectId))
              .thenReturn(true);

       projectService.deleteById(projectId);

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteByIdIfProjectDoesNotExist() {
        long projectId = 1L;

        when(projectRepository.existsById(projectId))
              .thenReturn(false);

        projectService.deleteById(projectId);

        verify(projectRepository, times(1)).existsById(projectId);
        verify(projectRepository, times(0)).deleteById(projectId);
    }

    @Test
    public void existByIdIfExist() {
        long projectId = 1L;

        when(projectRepository.existsById(projectId))
              .thenReturn(true);

        boolean actual = projectService.existById(projectId);

        verify(projectRepository, times(1)).existsById(projectId);
        assertTrue(actual);
    }

    @Test
    public void existByIdIfDoesNotExist() {
        long projectId = 1L;

        when(projectRepository.existsById(projectId))
              .thenReturn(false);

        boolean actual = projectService.existById(projectId);

        verify(projectRepository, times(1)).existsById(projectId);
        assertFalse(actual);
    }

}