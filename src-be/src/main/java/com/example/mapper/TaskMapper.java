package com.example.mapper;


import com.example.dto.TaskDto;
import com.example.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "assignedUser", ignore = true)
    @Mapping(target = "desk", ignore = true)
    Task taskDtoToTask(TaskDto taskDto);

    @Mapping(target = "assignedUser", ignore = true)
    TaskDto taskToTaskDto(Task task);

}
