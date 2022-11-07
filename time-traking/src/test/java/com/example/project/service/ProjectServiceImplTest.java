package com.example.project.service;

import com.example.project.model.ProjectEntity;
import com.example.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void canFetchAll() {

        // when
        List<ProjectEntity> projects = new ArrayList<>(){{
            add(ProjectEntity.builder().id(1L).build());
            add(ProjectEntity.builder().id(2L).build());
        }};

        List<ProjectEntity> newProjects = new ArrayList<>(){{
            add(ProjectEntity.builder().id(2L).name("ddd").build());
            add(ProjectEntity.builder().id(1L).build());
        }};
        when(projectRepository.findAll()).thenReturn(projects);


        // then
        assertThat(projectService.fetchAll()).hasSameElementsAs(newProjects);
    }

    @Test
    void canInsert() {
        // given
        ProjectEntity newProject = ProjectEntity.builder().id(1L).name("Clastix").build();

        // when
        projectService.insert(newProject);

        // then
        ArgumentCaptor<ProjectEntity> projectEntityArgumentCaptor =
                ArgumentCaptor.forClass(ProjectEntity.class);

        verify(projectRepository).save(projectEntityArgumentCaptor.capture());

        ProjectEntity capturedProject = projectEntityArgumentCaptor.getValue();

        assertThat(capturedProject).isEqualTo(newProject);
    }

    @Test
    void canDelete() {
        // given
        ProjectEntity newProject = ProjectEntity.builder().id(1L).name("Clastix").build();

        // when
        projectService.deleteById(newProject.getId());

        // then
        ArgumentCaptor<Long> projectIdArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(projectRepository).deleteById(projectIdArgumentCaptor.capture());

        Long capturedProject = projectIdArgumentCaptor.getValue();

        assertThat(capturedProject).isEqualTo(newProject.getId());
    }

}