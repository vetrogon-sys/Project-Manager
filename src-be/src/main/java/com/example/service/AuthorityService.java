package com.example.service;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.entity.security.Authority;
import com.example.entity.security.SecurityOpportunity;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AuthorityService {

    Authority createAuthority(Project project, User user, List<SecurityOpportunity> opportunities);

    Authority getBySignature(String signature);

    List<Authority> getAllByUserEmail(String email);

    List<GrantedAuthority> getAllByUserEmailAsGrantedAuthorities(String email);

    boolean existsByProjectIdAndAssignedEmailEquals(Long projectId, String assignedEmail);

    boolean existByDeskInProjectIdAndAssignedUserEmail(Long deskId, String assignedEmail);

    boolean existsByTaskInProjectIdAndAssignedUserEmail(Long taskId, String assignedEmail);

}
