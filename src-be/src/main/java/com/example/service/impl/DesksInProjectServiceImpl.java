package com.example.service.impl;

import com.example.dto.DeskDto;
import com.example.entity.Desk;
import com.example.entity.Project;
import com.example.mapper.DeskMapper;
import com.example.service.DeskService;
import com.example.service.DesksInProjectService;
import com.example.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesksInProjectServiceImpl implements DesksInProjectService {

    private final DeskService deskService;
    private final ProjectService projectService;
    private final DeskMapper deskMapper;

    @Override
    public List<DeskDto> getAllDesksFromProjectById(Long projectId) {
        if (!projectService.existById(projectId)) {
            throw new EntityNotFoundException(String.format("Can't create Desk in project with id: %d because it doesn't exist", projectId));
        }
        return deskService.getAllByProjectIdEquals(projectId).stream()
              .map(deskMapper::deskToDeskDto)
              .collect(Collectors.toList());
    }

    @Override
    public DeskDto createDeskInProject(Long projectId, DeskDto deskDto) {
        Project project = projectService.getById(projectId);

        Desk desk = deskMapper.deskDtoToDesk(deskDto);
        desk.setProject(project);
        return deskMapper.deskToDeskDto(deskService.create(desk));
    }

    @Override
    public void deleteDeskById(Long deskId) {
        deskService.deleteById(deskId);
    }
}
