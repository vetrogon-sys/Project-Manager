package com.example.evaluator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GlobalPermissionEvaluator implements PermissionEvaluator {
    private final List<TypePermissionEvaluator> permissionEvaluators;
    private Map<String, TypePermissionEvaluator> permissionEvaluatorMap;

    @PostConstruct
    public void init() {
        permissionEvaluatorMap = permissionEvaluators.stream()
              .collect(Collectors.toConcurrentMap(TypePermissionEvaluator::getMyType, Function.identity()));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return permissionEvaluatorMap.get(targetDomainObject.toString())
              .hasPermission(authentication, targetDomainObject, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return permissionEvaluatorMap.get(targetType)
              .hasPermission(authentication, targetId, targetType, permission);
    }
}
