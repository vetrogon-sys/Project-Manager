package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/assignTo/projects/{projectId}")
    public ResponseEntity<Page<UserDto>> getAssignedToProjectById(@PathVariable Long projectId) {

        PageRequest pageable = PageRequest.of(0, 5);
        return ResponseEntity
              .ok(userService.getAllAssignedToProjectWithId(projectId, pageable));
    }

    @GetMapping("/aboutme")
    public ResponseEntity<UserDto> aboutMe(HttpServletRequest request) {
        return ResponseEntity
              .ok(userService.getUserByEmailAsDto(request.getRemoteUser()));
    }

}
