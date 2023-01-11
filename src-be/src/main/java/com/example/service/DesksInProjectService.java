package com.example.service;

import com.example.dto.DeskDto;

import java.util.List;

public interface DesksInProjectService {

    List<DeskDto> getAllDesksFromProjectById(Long projectId);

    DeskDto createDeskInProject(Long projectId, DeskDto deskDto);

    void deleteDeskById(Long deskId);

}
