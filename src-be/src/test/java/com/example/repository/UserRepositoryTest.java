package com.example.repository;

import com.example.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql({"users/insert_users.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    private static final String EXISTING_EMAIL = "user_test@gmail.com";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailTestIfUserExist() {
        Optional<User> actualUser = userRepository.findByEmail(EXISTING_EMAIL);

        User expectedUser = getUserEqualsToExisting();

        assertTrue(actualUser.isPresent());
        assertEquals(expectedUser, actualUser.get());
    }

    @Test
    public void findByEmailTestIfUserDoesNotExist() {
        Optional<User> actualUser = userRepository.findByEmail("non-existed-email@gmail.com");

        assertTrue(actualUser.isEmpty());
    }

    private User getUserEqualsToExisting() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setFirstName("Adam");
        user.setLastName("Green");
        return user;
    }

}