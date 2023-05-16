package com.example.repository;

import com.example.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    
    List<Authority> findAllByAssignedEmail(String email);

    Optional<Authority> findBySignature(String signature);
    boolean existsByRelatedProjectIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(Long projectId, String assignedEmail, String opportunity);

    boolean existsByRelatedProjectDesksIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(Long deskId, String assignedEmail, String opportunity);

    boolean existsByRelatedProjectDesksTasksIdEqualsAndAssignedEmailEqualsAndOpportunitiesOpportunityEquals(Long taskId, String assignedEmail, String opportunity);

}
