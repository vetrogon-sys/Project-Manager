package com.example.service;

import com.example.entity.Project;

public interface ProjectService {

    Project getById(Long projectId);

    Project create(Project project);

    Project update(Project project);

    void deleteById(Long projectId);

    boolean existById(Long projectId);

}
