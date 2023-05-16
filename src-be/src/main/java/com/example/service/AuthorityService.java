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

    boolean existsByProjectIdAndPermissionAndAssignedEmailEquals(Long projectId, String assignedEmail, String permission);

    boolean existByDeskInProjectIdAndPermissionAndAssignedUserEmail(Long deskId, String assignedEmail, String permission);

    boolean existsByTaskInProjectIdAndPermissionAndAssignedUserEmail(Long taskId, String assignedEmail, String permission);

}
