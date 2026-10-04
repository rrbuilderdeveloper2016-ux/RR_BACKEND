package com.rrbuilders.backend.service.impl;

import com.rrbuilders.backend.entity.Project;
import com.rrbuilders.backend.exception.ResourceNotFoundException;
import com.rrbuilders.backend.repository.ProjectRepository;
import com.rrbuilders.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects(String category, String status) {
        if (category != null && !category.isBlank() && !category.equalsIgnoreCase("all")) {
            return projectRepository.findByCategoryIgnoreCase(category.trim());
        }
        if (status != null && !status.isBlank() && !status.equalsIgnoreCase("all")) {
            return projectRepository.findByStatusIgnoreCase(status.trim());
        }
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
    }

    @Override
    public List<Project> getFeaturedProjects() {
        return projectRepository.findAll().stream().limit(3).toList();
    }

    @Override
    @Transactional
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}
