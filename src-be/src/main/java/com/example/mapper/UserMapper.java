package com.example.mapper;

import com.example.dto.UserDto;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "ownProjects", ignore = true),
            @Mapping(target = "assignedProjects", ignore = true),
    })
    User userDtoToUser(UserDto userDto);

    @Mappings({
            @Mapping(target = "ownProjects", ignore = true),
            @Mapping(target = "assignedProjects", ignore = true),
    })
    UserDto userToUserDto(User user);

}
