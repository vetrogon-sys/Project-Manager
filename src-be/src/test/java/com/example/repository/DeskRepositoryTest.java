package com.example.repository;

import com.example.entity.Desk;
import com.example.entity.Project;
import com.example.entity.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql({"desks/insert_desks.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeskRepositoryTest {

    @Autowired
    private DeskRepository deskRepository;

    @Test
    public void findAllWithTasksIfProjectExist() {
        Page<Desk> actualDesks =
                deskRepository.findAllByProjectIdEquals(1L, Pageable.ofSize(3));


        assertNotNull(actualDesks);
        assertEquals(3, actualDesks.getContent().size());

        List<Desk> expectedDesks = getExpectedDesksUsingProjectProxy(actualDesks.getContent().get(0).getProject());
        assertEquals(expectedDesks, actualDesks.getContent());
    }

    @Test
    public void findAllWithTasksIfProjectDoesNotExist() {
        Page<Desk> actualDesks =
                deskRepository.findAllByProjectIdEquals(999L, Pageable.ofSize(3));

        assertNotNull(actualDesks);
        assertEquals(0, actualDesks.getContent().size());
    }

    private List<Desk> getExpectedDesksUsingProjectProxy(Project project) {

        User creator = new User();
        creator.setId(1L);
        creator.setEmail("user_test@gmail.com");
        creator.setFirstName("Adam");
        creator.setLastName("Green");

        project.setCreator(creator);
        return new ArrayList<>(
                Arrays.asList(
                        Desk.builder().id(1L).name("Discus").project(project).build(),
                        Desk.builder().id(2L).name("Development").project(project).build(),
                        Desk.builder().id(3L).name("To Test").project(project).build()

                )
        );
    }

}