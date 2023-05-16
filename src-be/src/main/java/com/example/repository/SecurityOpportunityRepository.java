package com.example.repository;

import com.example.entity.security.SecurityOpportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOpportunityRepository extends JpaRepository<SecurityOpportunity, String> {
}
