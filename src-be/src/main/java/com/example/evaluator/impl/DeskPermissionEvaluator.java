package com.example.evaluator.impl;

import com.example.entity.Project;
import com.example.evaluator.TypePermissionEvaluator;
import com.example.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeskPermissionEvaluator implements TypePermissionEvaluator {

    private static final String TYPE = "Desk";
    private final AuthorityService authorityService;

    @Override
    public String getMyType() {
        return TYPE;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (!TYPE.equals(targetType)
              || Objects.isNull(targetId)) {
            return false;
        }
        return authorityService.existByDeskInProjectIdAndAssignedUserEmail((Long) targetId, authentication.getName());
    }
}
