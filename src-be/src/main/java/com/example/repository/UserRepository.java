package com.example.repository;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByAssignedTasksIdEquals(Long taskId);

    boolean existsByEmail(String email);

    Page<User> findAllByAssignedProjectsIdEquals(Long projectId, Pageable pageable);

    Page<User> findByIdNotIn(List<Long> userIds, Pageable pageable);
}
