package com.example.project.service;

import com.example.project.model.ProjectEntity;
import com.example.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectEntity> fetchAll() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectEntity getById(Long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    @Override
    public ProjectEntity insert(ProjectEntity project) {
        return projectRepository.save(project);
    }

    @Override
    public ProjectEntity update(Long id, ProjectEntity project) {
        ProjectEntity existingProject = projectRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(project, existingProject, "id");
        return existingProject;
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Optional<ProjectEntity> findById(Long id) {
        return projectRepository.findById(id);
    }
}
