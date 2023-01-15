package com.example.repository;

import com.example.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Page<Project> findAllByCreatorEmailEquals(String email, Pageable pageable);

    Page<Project> findAllByAssignedUsersEmailEquals(String email, Pageable pageable);

}
