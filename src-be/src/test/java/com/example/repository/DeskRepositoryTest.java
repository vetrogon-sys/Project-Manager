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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        List<Desk> actualDesks =
                deskRepository.findAllByProjectIdEquals(1L);


        assertNotNull(actualDesks);
        assertEquals(3, actualDesks.size());

        List<Desk> expectedDesks = getExpectedDesksUsingProjectProxy(actualDesks.get(0).getProject());
        assertEquals(expectedDesks, actualDesks);
    }

    @Test
    public void findAllWithTasksIfProjectDoesNotExist() {
        List<Desk> actualDesks =
                deskRepository.findAllByProjectIdEquals(999L);

        assertNotNull(actualDesks);
        assertEquals(0, actualDesks.size());
    }

    private List<Desk> getExpectedDesksUsingProjectProxy(Project project) {

        User creator = new User();
        creator.setId(1L);
        creator.setEmail("user_test@gmail.com");
        creator.setFirstName("Adam");
        creator.setLastName("Green");

        project.setCreator(creator);

        Desk d1 = new Desk();
        d1.setId(1L);
        d1.setName("Discus");
        d1.setProject(project);

        Desk d2 = new Desk();
        d2.setId(2L);
        d2.setName("Development");
        d2.setProject(project);

        Desk d3 = new Desk();
        d3.setId(3L);
        d3.setName("To Test");
        d3.setProject(project);

        return new ArrayList<>(
                Arrays.asList(d1, d2, d3)
        );
    }

}