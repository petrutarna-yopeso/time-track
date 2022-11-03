package com.example.project.service;

import com.example.project.model.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectEntity> fetchAll();

    Optional<ProjectEntity> getById(Long id);

    ProjectEntity insert(ProjectEntity project);

    void deleteById(Long id);
}
