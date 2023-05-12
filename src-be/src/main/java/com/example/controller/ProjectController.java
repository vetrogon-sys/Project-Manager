package com.example.controller;

import com.example.dto.ProjectDto;
import com.example.dto.UsersIdsList;
import com.example.service.ProjectService;
import com.example.service.UsersInProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UsersInProjectService usersInProjectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long projectId) {
        return ResponseEntity
              .ok(projectService.getByIdAsDto(projectId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> update(@PathVariable Long projectId,
                                             @RequestBody ProjectDto projectDto) {
        return ResponseEntity
              .ok(projectService.update(projectId, projectDto));
    }

    @GetMapping("/myself/created")
    public ResponseEntity<List<ProjectDto>> getCurrentUserCreatedProjects(Authentication principal,
                                                                          Pageable pageable) {
        return ResponseEntity
              .ok(projectService.getAllWhereUserWithEmailIsCreator(principal.getName(), pageable));
    }

    @GetMapping("/myself/assigned")
    public ResponseEntity<List<ProjectDto>> getCurrentUserAssignedProjects(Authentication principal,
                                                                           Pageable pageable) {
        return ResponseEntity
              .ok(projectService.getAllWhereUserWithEmailIsAssigned(principal.getName(), pageable));
    }

    @PutMapping("/{projectId}/assign/users/{usersIds}")
    public ResponseEntity<Void> assignUsersToProjects(@PathVariable Long projectId,
                                                      @PathVariable Long[] usersIds) {
        usersInProjectService.assignUsersByIdsToProjectById(projectId, List.of(usersIds));
        return ResponseEntity
              .ok()
              .build();
    }

    @PutMapping("/{projectId}/unassign/users")
    public ResponseEntity<Void> unasignUsersWithIdsFromProjectWithId(@PathVariable Long projectId,
                                                                     @RequestBody UsersIdsList idsList) {

        projectService.removeAssignedUsersWithIdsFromProjectWithId(idsList, projectId);
        return ResponseEntity
              .ok()
              .build();
    }

}
