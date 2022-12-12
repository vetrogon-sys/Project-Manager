package com.example.mapper;


import com.example.dto.TaskDto;
import com.example.entity.Task;
import org.mapstruct.factory.Mappers;

public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task taskDtoToTask(TaskDto taskDto);

    TaskDto taskToTaskDto(Task task);

}
