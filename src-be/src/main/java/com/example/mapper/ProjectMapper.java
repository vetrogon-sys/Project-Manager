package com.example.mapper;


import com.example.dto.ProjectDto;
import com.example.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "desks", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    Project projectDtoToProject(ProjectDto projectDto);

    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "assignedUsers", ignore = true)
    ProjectDto projectToProjectDto(Project project);

}
