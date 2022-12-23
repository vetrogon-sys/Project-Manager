package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String title;
    private String description;

    private LocalDate creationDate;
    private LocalDate reqResolutionDate;

    private UserDto assignedUser;

    private DeskDto desk;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(id, taskDto.id) &&
                Objects.equals(title, taskDto.title) &&
                Objects.equals(description, taskDto.description) &&
                Objects.equals(creationDate, taskDto.creationDate) &&
                Objects.equals(reqResolutionDate, taskDto.reqResolutionDate) &&
                Objects.equals(desk, taskDto.desk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, reqResolutionDate, desk);
    }
}
