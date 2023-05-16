package com.example.service.impl;

import com.example.entity.security.SecurityOpportunity;
import com.example.repository.SecurityOpportunityRepository;
import com.example.service.SecurityOpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityOpportunityServiceImpl implements SecurityOpportunityService {

    private static final List<String> BASIC_OPPORTUNITIES_IDS = List.of("edit", "get");
    private final SecurityOpportunityRepository securityOpportunityRepository;

    @Override
    public List<SecurityOpportunity> getAll() {
        return securityOpportunityRepository.findAll();
    }

    @Override
    public List<SecurityOpportunity> getBasicOpportunities() {
        return securityOpportunityRepository.findAllById(BASIC_OPPORTUNITIES_IDS);
    }

    @Override
    public SecurityOpportunity getById(String opportunity) {
        return securityOpportunityRepository.findById(opportunity)
              .orElseThrow(() -> new EntityNotFoundException(String.format("Can't find security-opportunity `%s`", opportunity)));
    }
}
