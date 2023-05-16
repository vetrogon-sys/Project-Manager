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

    boolean existsByRelatedProjectIdEqualsAndAssignedEmailEquals(Long projectId, String assignedEmail);

    boolean existsByRelatedProjectDesksIdEqualsAndAssignedEmailEquals(Long deskId, String assignedEmail);

    boolean existsByRelatedProjectDesksTasksIdEqualsAndAssignedEmailEquals(Long taskId, String assignedEmail);

}
