package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    public void getByEmailIfUserWithEmailExist() {
        User expectedUser = TestConstants.getUserEqualsToExisting();

        when(userRepository.findByEmail(TestConstants.EXISTING_EMAIL))
                .thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getByEmail(TestConstants.EXISTING_EMAIL);

        verify(userRepository, times(1)).findByEmail(TestConstants.EXISTING_EMAIL);
        assertEquals(expectedUser, actualUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByEmailIfUserWithEmailDoesNotExist() {
        when(userRepository.findByEmail(TestConstants.NON_EXISTED_EMAIL))
                .thenReturn(Optional.empty());

        userService.getByEmail(TestConstants.NON_EXISTED_EMAIL);

        verify(userRepository, times(1)).findByEmail(TestConstants.NON_EXISTED_EMAIL);
    }

    @Test
    public void getUserByEmailAsDtoIfUserExist() {
        User user = TestConstants.getUserEqualsToExisting();
        UserDto expectedUser = TestConstants.getUserDtoEqualsToExisting();

        when(userRepository.findByEmail(TestConstants.EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user))
                .thenReturn(expectedUser);

        UserDto actualUser = userService.getUserByEmailAsDto(TestConstants.EXISTING_EMAIL);

        verify(userRepository, times(1)).findByEmail(TestConstants.EXISTING_EMAIL);
        assertEquals(expectedUser, actualUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getByEmailAsDtoIfUserDoesNotExist() {
        when(userRepository.findByEmail(TestConstants.NON_EXISTED_EMAIL))
                .thenReturn(Optional.empty());

        userService.getUserByEmailAsDto(TestConstants.NON_EXISTED_EMAIL);

        verify(userRepository, times(1)).findByEmail(TestConstants.NON_EXISTED_EMAIL);
    }

}