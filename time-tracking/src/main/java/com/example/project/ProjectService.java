package com.example.project;

import com.example.project.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<ProjectEntity> fetchAll();

    ProjectEntity getById(Long id);

    ProjectEntity insert(ProjectEntity project);

    ProjectEntity update(Long id, ProjectEntity project);

    void deleteById(Long id);

    Optional<ProjectEntity> findById(Long id);
}
