package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    @NotBlank(message = "Task title can't be blank")
    @Size(min = 5, max = 125, message = "Task title must be between 5 and 125 symbols")
    private String title;
    @Size(max = 512, message = "Task description must be less than 512 symbols")
    private String description;

    private LocalDate creationDate;
    @Future(message = "You can chose only day in future to resolute this task")
    private LocalDate reqResolutionDate;

    private UserDto assignedUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) &&
                Objects.equals(title, taskDto.title) &&
                Objects.equals(description, taskDto.description) &&
                Objects.equals(creationDate, taskDto.creationDate) &&
                Objects.equals(reqResolutionDate, taskDto.reqResolutionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, reqResolutionDate);
    }
}
