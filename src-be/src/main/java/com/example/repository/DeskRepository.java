package com.example.repository;

import com.example.entity.Desk;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {

    List<Desk> findAllByProjectIdEquals(Long projectId);

    @EntityGraph(value = "desk-with-tasks")
    Optional<Desk> findWithTasksById(Long deskId);

}
