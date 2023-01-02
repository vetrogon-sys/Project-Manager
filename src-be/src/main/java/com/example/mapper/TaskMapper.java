package com.example.mapper;


import com.example.dto.TaskDto;
import com.example.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mappings({
            @Mapping(target = "desk.tasks", ignore = true),
            @Mapping(target = "desk.project", ignore = true),
    })
    Task taskDtoToTask(TaskDto taskDto);

    @Mappings({
            @Mapping(target = "desk", ignore = true),
            @Mapping(target = "assignedUser", ignore = true),
    })
    TaskDto taskToTaskDto(Task task);

}
