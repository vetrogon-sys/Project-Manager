package com.example.controller;

import com.example.dto.DeskDto;
import com.example.service.DesksInProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/desks")
@RequiredArgsConstructor
public class DeskController {

    private final DesksInProjectService desksInProjectService;

    @GetMapping
    public ResponseEntity<List<DeskDto>> getAllDesksFromProject(@PathVariable Long projectId) {

        return ResponseEntity
              .ok(desksInProjectService.getAllDesksFromProjectById(projectId));
    }

    @PostMapping
    public ResponseEntity<Void> createDesksInProject(@PathVariable Long projectId, @RequestBody DeskDto deskDto,
                                                     HttpServletRequest request) {
        DeskDto created = desksInProjectService.createDeskInProject(projectId, deskDto);

        URI createdTaskUri = URI.create(request.getRequestURL()
              .append("/")
              .append(created.getId())
              .toString());
        return ResponseEntity
              .created(createdTaskUri)
              .build();
    }

    @DeleteMapping("/{deskId}")
    public ResponseEntity<Void> deleteDesksInProject(@PathVariable Long deskId) {
        desksInProjectService.deleteDeskById(deskId);

        return ResponseEntity
              .ok()
              .build();
    }

}
