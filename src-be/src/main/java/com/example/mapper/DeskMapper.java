package com.example.mapper;

import com.example.dto.DeskDto;
import com.example.entity.Desk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeskMapper {

    Desk deskDtoToDesk(DeskDto deskDto);

    DeskDto deskToDeskDto(Desk desk);

}
