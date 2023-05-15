package com.example.repository;

import com.example.config.AutoConfigureH2TestDatabase;
import com.example.config.FillDatabaseWithTestedData;
import com.example.constants.TestConstants;
import com.example.entity.security.Authority;
import com.example.entity.security.SecurityOpportunity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureH2TestDatabase
@FillDatabaseWithTestedData
public class AuthorityRepositoryTest {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Test
    public void testFindAllByAssignedEmail() {
        List<Authority> actualAuthorities = authorityRepository.findAllByAssignedEmail(TestConstants.EXISTING_EMAIL);

        Assert.assertNotNull(actualAuthorities);
        Assert.assertThat(actualAuthorities.size(), is(2));
        Assert.assertEquals("user_1_project_1_Create_Delete", actualAuthorities.get(0).getSignature());
        Assert.assertEquals("user_1_project_2_Create", actualAuthorities.get(1).getSignature());
    }

    @Test
    public void testFindBySignature() {
        Optional<Authority> actualAuthority = authorityRepository.findBySignature("user_1_project_1_Create_Delete");
        List<SecurityOpportunity> expectedOpportunities = TestConstants.getSecurityOpportunities();

        Assert.assertTrue(actualAuthority.isPresent());
        Assert.assertThat(actualAuthority.get().getId(), is(1L));
        Assert.assertEquals(expectedOpportunities, actualAuthority.get().getOpportunities());
    }
}