package com.example.repository;

import com.example.entity.Desk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {

    @EntityGraph(value = "desk-with-tasks")
    Page<Desk> findAll(Pageable pageable);

    Optional<Desk> findById(Long id);

}
