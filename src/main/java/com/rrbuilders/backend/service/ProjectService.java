package com.rrbuilders.backend.service;

import com.rrbuilders.backend.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(String category, String status);

    Project getProjectById(String id);

    List<Project> getFeaturedProjects();

    Project saveProject(Project project);
}
