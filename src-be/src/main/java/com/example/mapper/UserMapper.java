package com.example.mapper;

import com.example.dto.RegistrationDto;
import com.example.dto.UserDto;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "ownProjects", ignore = true)
    @Mapping(target = "assignedProjects", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    User registrationDtoToUser(RegistrationDto registrationDto);

    UserDto userToUserDto(User user);

}
