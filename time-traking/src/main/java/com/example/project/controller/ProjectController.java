package com.example.project.controller;

import com.example.project.model.ProjectEntity;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/project/"})
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private  final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getAll() {return ResponseEntity.ok(projectService.fetchAll());};

    @GetMapping("{project_id}")
    public ResponseEntity<ProjectEntity> getDetails(@PathVariable(name = "project_id") Long id) {
        return ResponseEntity.ok(projectService.getById(id));};

    @PostMapping
    public ResponseEntity<ProjectEntity> addProject(@Valid @RequestBody ProjectEntity newProject) {
        return ResponseEntity.ok(projectService.insert(newProject));
    }

    @PutMapping("{project_id}")
    public ResponseEntity<ProjectEntity> updateProject(
            @PathVariable(name = "project_id") Long id,
            @Valid @RequestBody ProjectEntity newProject
    ) {
        return ResponseEntity.ok(projectService.update(id, newProject));
    }

    @DeleteMapping("{project_id}")
    public ResponseEntity<?> deleteProject(@PathVariable(name = "project_id") Long id) {
        try {
            projectService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            log.error(String.format("Can't delete project with id: %d .", id), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    };
}
