package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/aboutme")
    public ResponseEntity<UserDto> aboutMe(HttpServletRequest request) {
        return ResponseEntity
              .ok(userService.getUserByEmailAsDto(request.getRemoteUser()));
    }

}
