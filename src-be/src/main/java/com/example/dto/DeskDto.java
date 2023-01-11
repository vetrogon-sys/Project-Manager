package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeskDto {

    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeskDto deskDto = (DeskDto) o;
        return Objects.equals(id, deskDto.id) &&
                Objects.equals(name, deskDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
