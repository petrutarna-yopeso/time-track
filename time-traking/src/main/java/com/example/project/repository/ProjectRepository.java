package com.example.project.repository;

import com.example.project.model.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    @Override
    List<ProjectEntity> findAll();
}

