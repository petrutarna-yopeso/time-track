package com.example.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    @Override
    List<ProjectEntity> findAll();
}

