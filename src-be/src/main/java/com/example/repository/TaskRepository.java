package com.example.repository;

import com.example.entity.Desk;
import com.example.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByDesk(Desk desk, Pageable pageable);

    Page<Task> findAllByDeskIdEquals(Long deskId, Pageable pageable);

    @EntityGraph(value = "task-with-desk")
    Optional<Task> findWithDeskById(Long aLong);

}
