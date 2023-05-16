package com.example.evaluator;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

public interface TypePermissionEvaluator {

    String getMyType();

    boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission);

    boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission);

}
