package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.dto.DeskDto;
import com.example.entity.Desk;
import com.example.entity.Project;
import com.example.mapper.DeskMapper;
import com.example.service.DeskService;
import com.example.service.DesksInProjectService;
import com.example.service.ProjectService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DesksInProjectServiceImplTest {

    @Mock
    private DeskService deskService;

    @Mock
    private ProjectService projectService;

    @Mock
    private DeskMapper deskMapper;

    private DesksInProjectService desksInProjectService;

    @Before
    public void setUp() throws Exception {
        desksInProjectService = new DesksInProjectServiceImpl(deskService, projectService, deskMapper);
    }

    @Test
    public void testGetAllDesksFromProjectByIdIfProjectIsExist() {
        long projectId = 1L;

        when(projectService.existById(projectId))
              .thenReturn(true);
        when(deskService.getAllByProjectIdEquals(projectId))
              .thenReturn(TestConstants.getDeskListWithProjectcId(projectId));
        when(deskMapper.deskToDeskDto(any(Desk.class)))
              .thenReturn(TestConstants.getDeskDto());

        List<DeskDto> actualList = desksInProjectService.getAllDesksFromProjectById(projectId);

        verify(projectService, times(1)).existById(projectId);
        verify(deskService, times(1)).getAllByProjectIdEquals(projectId);
        verify(deskMapper, times(3)).deskToDeskDto(any(Desk.class));

        List<DeskDto> expectedList = new ArrayList<>(List.of(
              TestConstants.getDeskDto(), TestConstants.getDeskDto(), TestConstants.getDeskDto()
        ));
        assertEquals(expectedList, actualList);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetAllDesksFromProjectByIdIfProjectDoesNotExist() {
        long projectId = 1L;

        when(projectService.existById(projectId))
              .thenReturn(false);

        desksInProjectService.getAllDesksFromProjectById(projectId);

        verify(projectService, times(1)).existById(projectId);
        verifyNoInteractions(deskService.getAllByProjectIdEquals(anyLong()));
        verifyNoInteractions(deskMapper.deskToDeskDto(any(Desk.class)));
    }

    @Test
    public void testCreateDeskInProject() {
        long projectId = 1L;
        Project project = TestConstants.getProjectWithId(projectId);
        DeskDto deskDto = TestConstants.getDeskDto();
        Desk desk = TestConstants.getDesk();

        when(projectService.getById(projectId))
              .thenReturn(project);
        when(deskMapper.deskDtoToDesk(deskDto))
              .thenReturn(desk);
        when(deskService.create(any(Desk.class)))
              .thenAnswer(i -> i.getArguments()[0]);
        when(deskMapper.deskToDeskDto(any(Desk.class)))
              .thenReturn(deskDto);

        DeskDto actualDto = desksInProjectService.createDeskInProject(projectId, deskDto);

        verify(projectService, times(1)).getById(projectId);
        verify(deskMapper, times(1)).deskDtoToDesk(any(DeskDto.class));
        verify(deskService, times(1)).create(any(Desk.class));
        verify(deskMapper, times(1)).deskDtoToDesk(any(DeskDto.class));
        assertEquals(deskDto, actualDto);
    }

    @Test
    public void testDeleteDeskById() {
        long deskId = 1L;

        desksInProjectService.deleteDeskById(deskId);

        verify(deskService, times(1)).deleteById(deskId);
    }
}