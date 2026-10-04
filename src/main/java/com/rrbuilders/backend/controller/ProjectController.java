package com.rrbuilders.backend.controller;

import com.rrbuilders.backend.dto.ApiResponse;
import com.rrbuilders.backend.entity.Project;
import com.rrbuilders.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Project>>> getAllProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status) {
        List<Project> projects = projectService.getAllProjects(category, status);
        return ResponseEntity.ok(ApiResponse.success("Projects fetched successfully", projects));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Project>> getProjectById(@PathVariable String id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success("Project fetched successfully", project));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<Project>>> getFeaturedProjects() {
        List<Project> projects = projectService.getFeaturedProjects();
        return ResponseEntity.ok(ApiResponse.success("Featured projects fetched successfully", projects));
    }
}
