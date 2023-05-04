package com.example.service.impl;

import com.example.constants.TestConstants;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.entity.security.Authority;
import com.example.entity.security.SecurityOpportunity;
import com.example.repository.AuthorityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorityServiceImplTest {

    @Mock
    private AuthorityRepository authorityRepository;

    private AuthorityServiceImpl authorityService;

    @Before
    public void setUp() throws Exception {
        authorityService = new AuthorityServiceImpl(authorityRepository);
    }

    @Test
    public void testCreateAuthority() {
        Project project = TestConstants.getProjectWithId(1L);
        User user = TestConstants.getUserEqualsToExisting();
        List<SecurityOpportunity> opportunities = TestConstants.getSecurityOpportunities();

        when(authorityRepository.save(any(Authority.class)))
              .thenAnswer(i -> i.getArguments()[0]);

        Authority actualAuthority = authorityService.createAuthority(project, user, opportunities);

        Authority expectedAuthority = TestConstants.getExistingAuthority();

        verify(authorityRepository, times(1)).save(any(Authority.class));
        Assert.assertNotNull(actualAuthority);
        Assert.assertEquals(expectedAuthority, actualAuthority);
    }

    @Test
    public void testGetBySignatureIfExist() {
        String authoritySignature = TestConstants.EXISTING_AUTHORITY_SIGNATURE;

        when(authorityRepository.findBySignature(authoritySignature))
              .thenReturn(Optional.of(TestConstants.getExistingAuthority()));

        Authority actualAuthority
              = authorityService.getBySignature(authoritySignature);

        verify(authorityRepository, times(1)).findBySignature(authoritySignature);
        Assert.assertNotNull(actualAuthority);
        Assert.assertEquals(TestConstants.getExistingAuthority(), actualAuthority);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetBySignatureIfDoesNotExist() {
        String authoritySignature = "undefined";

        when(authorityRepository.findBySignature(authoritySignature))
              .thenReturn(Optional.empty());

        authorityService.getBySignature(authoritySignature);

        verify(authorityRepository, times(1)).findBySignature(authoritySignature);
    }

    @Test
    public void testGetAllByUserEmailIfExist() {
        String userEmail = TestConstants.EXISTING_EMAIL;

        when(authorityRepository.findAllByAssignedEmail(userEmail))
              .thenReturn(List.of(TestConstants.getExistingAuthority()));

        List<Authority> actualAuthorities = authorityService.getAllByUserEmail(userEmail);

        verify(authorityRepository, times(1)).findAllByAssignedEmail(userEmail);
        Assert.assertNotNull(actualAuthorities);
        Assert.assertThat(actualAuthorities.size(), is(1));
        Assert.assertEquals(List.of(TestConstants.getExistingAuthority()), actualAuthorities);
    }

    @Test
    public void testGetAllByUserEmailIfDoesNotExist() {
        String userEmail = TestConstants.NON_EXISTED_EMAIL;

        when(authorityRepository.findAllByAssignedEmail(userEmail))
              .thenReturn(new ArrayList<>());

        List<Authority> actualAuthorities = authorityService.getAllByUserEmail(userEmail);

        verify(authorityRepository, times(1)).findAllByAssignedEmail(userEmail);
        Assert.assertNotNull(actualAuthorities);
        Assert.assertThat(actualAuthorities.size(), is(0));
        Assert.assertEquals(new ArrayList<>(), actualAuthorities);
    }

    @Test
    public void testGetAllByUserEmailAsGrantedAuthoritiesIfUserExist() {
        String userEmail = TestConstants.EXISTING_EMAIL;

        when(authorityRepository.findAllByAssignedEmail(userEmail))
              .thenReturn(List.of(TestConstants.getExistingAuthority()));

        List<GrantedAuthority> actualAuthorities = authorityService.getAllByUserEmailAsGrantedAuthorities(userEmail);

        List<SimpleGrantedAuthority> expectedAuthorities = Stream.of(TestConstants.getExistingAuthority())
              .map(authority -> new SimpleGrantedAuthority(authority.getSignature()))
              .collect(Collectors.toList());

        verify(authorityRepository, times(1)).findAllByAssignedEmail(userEmail);
        Assert.assertNotNull(actualAuthorities);
        Assert.assertThat(actualAuthorities.size(), is(1));
        Assert.assertEquals(expectedAuthorities, actualAuthorities);
    }

    @Test
    public void testGetAllByUserEmailAsGrantedAuthoritiesIfUserDoesNotExist() {
        String userEmail = TestConstants.NON_EXISTED_EMAIL;

        when(authorityRepository.findAllByAssignedEmail(userEmail))
              .thenReturn(new ArrayList<>());

        List<GrantedAuthority> actualAuthorities = authorityService.getAllByUserEmailAsGrantedAuthorities(userEmail);

        verify(authorityRepository, times(1)).findAllByAssignedEmail(userEmail);
        Assert.assertNotNull(actualAuthorities);
        Assert.assertThat(actualAuthorities.size(), is(0));
        Assert.assertEquals(new ArrayList<>(), actualAuthorities);
    }

}