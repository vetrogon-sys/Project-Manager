package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.UsersIdsList;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/exclusions/{exclusionsIds}")
    public ResponseEntity<Page<UserDto>> findAllExclusions(@PathVariable Long[] exclusionsIds,
                                                           Pageable pageable) {
        return ResponseEntity
              .ok(userService.getAllExclusionsIds(new UsersIdsList(List.of(exclusionsIds)), pageable));
    }

    @GetMapping("/assignTo/projects/{projectId}")
    public ResponseEntity<Page<UserDto>> getAssignedToProjectById(@PathVariable Long projectId,
                                                                  Pageable pageable) {
        return ResponseEntity
              .ok(userService.getAllAssignedToProjectWithId(projectId, pageable));
    }

    @GetMapping("/assignTo/tasks/{taskId}")
    public ResponseEntity<UserDto> getAssignedToTaskById(@PathVariable Long taskId) {
        return ResponseEntity
              .ok(userService.getAssignedToTaskWithId(taskId));
    }

    @RequestMapping(value = "/assignTo/tasks/{taskId}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> isAssignedToTaskByIdExist(@PathVariable Long taskId) {
        return Boolean.TRUE.equals(userService.isAssignedToTaskWithIdExist(taskId)) ?
              ResponseEntity.ok().build()
              : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/aboutme")
    public ResponseEntity<UserDto> aboutMe(HttpServletRequest request) {
        return ResponseEntity
              .ok(userService.getUserByEmailAsDto(request.getRemoteUser()));
    }

}
