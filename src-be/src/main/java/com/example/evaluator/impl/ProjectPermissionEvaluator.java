package com.example.evaluator.impl;

import com.example.evaluator.TypePermissionEvaluator;
import com.example.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProjectPermissionEvaluator implements TypePermissionEvaluator {

    private static final String TYPE = "PROJECT";
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
        return authorityService.existsByProjectIdAndAssignedEmailEquals((Long) targetId, authentication.getName());
    }
}
