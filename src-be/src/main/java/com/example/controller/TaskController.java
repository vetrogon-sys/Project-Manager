package com.example.controller;

import com.example.dto.TaskDto;
import com.example.service.TaskService;
import com.example.service.TasksInDeskService;
import com.example.service.UsersTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/desks/{deskId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TasksInDeskService tasksInDeskService;

    private final TaskService taskService;

    private final UsersTasksService usersTasksService;

    @PreAuthorize("hasPermission(#deskId, 'Desk', 'edit')")
    @PostMapping
    public ResponseEntity<Void> createTask(@PathVariable Long deskId, @Valid @RequestBody TaskDto taskDto,
                                           HttpServletRequest request) {

        TaskDto createdTask = tasksInDeskService.createTaskInDesk(deskId, taskDto);

        URI createdTaskUri = URI.create(request.getRequestURL()
                .append("/")
                .append(createdTask.getId())
                .toString());
        return ResponseEntity
                .created(createdTaskUri)
                .build();
    }

    @PreAuthorize("hasPermission(#deskId, 'Desk', 'edit')")
    @PatchMapping("/{taskId}/moveTo/desks/{anotherDeskId}")
    public ResponseEntity<Void> moveTaskToAnotherDesk(@PathVariable Long deskId, @PathVariable Long taskId,
                                                      @PathVariable Long anotherDeskId) {
        tasksInDeskService.moveTaskToAnotherDesk(taskId, deskId, anotherDeskId);

        return ResponseEntity
                .ok().build();
    }

    @PreAuthorize("hasPermission(#deskId, 'Desk', 'edit')")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTaskFromDesk(@PathVariable Long deskId, @PathVariable Long taskId) {
        tasksInDeskService.deleteTaskInDesk(deskId, taskId);

        return ResponseEntity
                .ok().build();
    }

    @PreAuthorize("hasPermission(#deskId, 'Desk', 'get')")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasksFromDesk(@PathVariable Long deskId, Pageable pageable) {
        return ResponseEntity
                .ok(tasksInDeskService.getTasksFromDesk(deskId, pageable).getContent());
    }

    @PreAuthorize("hasPermission(#taskId, 'Task', 'edit')")
    @PatchMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        tasksInDeskService.updateTask(taskId, taskDto);

        return ResponseEntity
                .ok().build();
    }

    @PreAuthorize("hasPermission(#taskId, 'Task', 'edit')")
    @PatchMapping("/{taskId}/users/{userId}")
    public ResponseEntity<Void> assignUserByIdToTaskById(@PathVariable Long taskId,
                                                         @PathVariable Long userId) {
        usersTasksService.assignUserWithIdToTaskWithId(userId, taskId);
        return ResponseEntity
              .ok().build();
    }

    @PreAuthorize("hasPermission(#taskId, 'Task', 'edit')")
    @DeleteMapping("/{taskId}/users")
    public ResponseEntity<Void> unassignUserFromTaskById(@PathVariable Long taskId) {
        taskService.unassignUserFromTaskWithId(taskId);
        return ResponseEntity
              .ok().build();
    }

}
