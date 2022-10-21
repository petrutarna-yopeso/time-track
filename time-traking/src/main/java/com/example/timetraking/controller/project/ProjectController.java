package com.example.timetraking.controller.project;

import com.example.project.model.ProjectEntity;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/project"})
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private  final ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<ProjectEntity>> getAll() {return ResponseEntity.ok(projectService.fetchAll());};
}
