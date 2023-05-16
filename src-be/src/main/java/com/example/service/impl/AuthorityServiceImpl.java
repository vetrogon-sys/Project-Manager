package com.example.service.impl;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.entity.security.Authority;
import com.example.entity.security.SecurityOpportunity;
import com.example.repository.AuthorityRepository;
import com.example.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private static final String USER_VALUE = "user";
    private static final String PROJECT_VALUE = "project";
    private static final String UNDERSCORE = "_";

    private final AuthorityRepository authorityRepository;

    @Override
    public Authority createAuthority(Project project, User user, List<SecurityOpportunity> opportunities) {
        Authority authority = new Authority();
        authority.setAssigned(user);
        authority.setRelatedProject(project);
        authority.setOpportunities(opportunities);
        authority.setSignature(buildAuthoritySignature(user.getId(), project.getId(), opportunities));
        return authorityRepository.save(authority);
    }

    @Override
    public Authority getBySignature(String signature) {
        return authorityRepository.findBySignature(signature)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find authority with signature: %s", signature)));
    }

    @Override
    public List<Authority> getAllByUserEmail(String email) {
        return authorityRepository.findAllByAssignedEmail(email);
    }

    @Override
    public List<GrantedAuthority> getAllByUserEmailAsGrantedAuthorities(String email) {
        return getAllByUserEmail(email).stream()
              .map(auth -> new SimpleGrantedAuthority(auth.getSignature()))
              .collect(Collectors.toList());
    }

    @Override
    public boolean existsByProjectIdAndPermissionAndAssignedEmailEquals(Long projectId, String assignedEmail, String permission) {
        return authorityRepository.existsByRelatedProjectIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(projectId, assignedEmail, permission);
    }

    @Override
    public boolean existByDeskInProjectIdAndPermissionAndAssignedUserEmail(Long deskId, String assignedEmail, String permission) {
        return authorityRepository.existsByRelatedProjectDesksIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(deskId, assignedEmail, permission);
    }

    @Override
    public boolean existsByTaskInProjectIdAndPermissionAndAssignedUserEmail(Long taskId, String assignedEmail, String permission) {
        return authorityRepository.existsByRelatedProjectDesksTasksIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(taskId, assignedEmail, permission);
    }

    private String buildAuthoritySignature(Long userId, Long projectId, List<SecurityOpportunity> opportunities) {
        return USER_VALUE
              + UNDERSCORE
              + userId
              + UNDERSCORE
              + PROJECT_VALUE
              + UNDERSCORE
              + projectId
              + UNDERSCORE
              + opportunities.stream()
              .map(SecurityOpportunity::getOpportunity)
              .collect(Collectors.joining(UNDERSCORE));
    }
}
