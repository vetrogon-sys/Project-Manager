package com.example.controller;

import com.example.dto.ProjectDto;
import com.example.service.ProjectService;
import com.example.service.UsersInProjectService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UsersInProjectService usersInProjectService;

    @PostMapping()
    public ResponseEntity<ProjectDto> create(@RequestBody ProjectDto projectDto,
                                             Principal principal) {

        return ResponseEntity
              .ok(usersInProjectService.createProject(projectDto, principal.getName()));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getById(@PathVariable Long projectId) {
        return ResponseEntity
              .ok(projectService.getByIdAsDto(projectId));
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectDto> update(@PathVariable Long projectId,
                                             @RequestBody ProjectDto projectDto) {
        return ResponseEntity
              .ok(projectService.update(projectId, projectDto));
    }

    @ApiOperation("Find all projects for current user")
    @GetMapping("/myself/created")
    public ResponseEntity<List<ProjectDto>> getCurrentUserCreatedProjects(Authentication principal,
                                                                          Pageable pageable) {
        return ResponseEntity
              .ok(projectService.getAllWhereUserWithEmailIsCreator(principal.getName(), pageable));
    }

    @ApiOperation("Find all projects where current user is assigned")
    @GetMapping("/myself/assigned")
    public ResponseEntity<List<ProjectDto>> getCurrentUserAssignedProjects(Authentication principal,
                                                                           Pageable pageable) {
        return ResponseEntity
              .ok(projectService.getAllWhereUserWithEmailIsAssigned(principal.getName(), pageable));
    }

    @PatchMapping("/{projectId}/users/{usersIds}")
    public ResponseEntity<Void> assignUsersToProjects(@PathVariable Long projectId,
                                                      @PathVariable Long[] usersIds) {
        usersInProjectService.assignUsersByIdsToProjectById(projectId, List.of(usersIds));
        return ResponseEntity
              .ok()
              .build();
    }

    @DeleteMapping("/{projectId}/users/{usersIds}")
    public ResponseEntity<Void> unasignUsersWithIdsFromProjectWithId(@PathVariable Long projectId,
                                                                     @PathVariable Long[] usersIds) {

        projectService.removeAssignedUsersWithIdsFromProjectWithId(List.of(usersIds), projectId);
        return ResponseEntity
              .ok()
              .build();
    }

}
