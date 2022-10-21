package com.example.project.repository;

import com.example.employee.model.EmployeeEntity;
import com.example.project.model.ProjectEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepository {

    private Long projectId = 0L;
    private List<ProjectEntity> projects = new ArrayList<ProjectEntity>(){{
        add(ProjectEntity.builder().Id(projectId++).build());
        add(ProjectEntity.builder().Id(projectId++).build());
    }};

    public Iterable<ProjectEntity> getAll() {
        return projects;
    }

    public Optional<ProjectEntity> findById(Long id) {
        return projects.stream().filter(c -> c.getId().equals(id)).findAny();
    }

    public ProjectEntity save (ProjectEntity newProject) {
        newProject.setId(projectId++);
        this.projects.add(newProject);
        return newProject;
    }

    public void deleteById (Long id) {
        Assert.notNull(id, "The given id must not be null!");
        projects.remove(this.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", id), 1);
        }));
    }
}

