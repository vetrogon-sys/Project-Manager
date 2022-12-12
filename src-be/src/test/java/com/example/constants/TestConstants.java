package com.example.constants;

import com.example.dto.UserDto;
import com.example.entity.User;

public class TestConstants {
    public static final String EXISTING_EMAIL = "user_test@gmail.com";
    public static final String NON_EXISTED_EMAIL = "non-existed-email@gmail.com";

    public static User getUserEqualsToExisting() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setFirstName("Adam");
        user.setLastName("Green");
        return user;
    }

    public static UserDto getUserDtoEqualsToExisting() {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setFirstName("Adam");
        user.setLastName("Green");
        return user;
    }
}
