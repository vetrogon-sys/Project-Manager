package com.example.service;

import com.example.entity.security.SecurityOpportunity;

import java.util.List;

public interface SecurityOpportunityService {

    List<SecurityOpportunity> getAll();

    List<SecurityOpportunity> getBasicOpportunities();

    SecurityOpportunity getById(String opportunity);

}
