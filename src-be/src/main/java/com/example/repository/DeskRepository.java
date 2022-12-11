package com.example.repository;

import com.example.entity.Desk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeskRepository extends JpaRepository<Desk, Long> {

    Page<Desk> findAllByProjectIdEquals(Long projectId, Pageable pageable);

}
