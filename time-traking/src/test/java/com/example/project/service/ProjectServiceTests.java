package com.example.project.service;

import com.example.project.model.ProjectEntity;
import com.example.project.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTests {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

//    @BeforeEach
//    private void addOneProject() {
//        projectService.insert(ProjectEntity.builder().name("Klarna").build());
//    }
    @Test
    void FetctAllProjects_HasOneEntry() {

        //when
        projectService.fetchAll();

        //then
        verify(projectRepository).getAll();
    }

}