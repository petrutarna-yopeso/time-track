package com.example.project.service;

import com.example.project.model.ProjectEntity;
import com.example.project.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void canFetchAllProjects() {

        // when
        List<ProjectEntity> projects = new ArrayList<>(){{
            add(ProjectEntity.builder().id(1L).build());
            add(ProjectEntity.builder().id(2L).build());
        }};

        List<ProjectEntity> newProjects = new ArrayList<>(){{
            add(ProjectEntity.builder().id(2L).build());
            add(ProjectEntity.builder().id(1L).build());
        }};
        when(projectRepository.findAll()).thenReturn(projects);

        // then
        assertThat(projectService.fetchAll()).hasSameElementsAs(newProjects);
    }



    @Test
    void canGetAProjectById() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Clastix").build();

        // when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        // then
        assertThat(projectService.getById(project.getId())).isEqualTo(project);
    }


    @Test
    void throwsNoSuchElementExceptionWhenCantGetAProjectById() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Klarna").build();

        // when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.empty());

        // then
        assertThat(projectService.findById(project.getId())).isEmpty();
    }

    @Test
    void canInsertAProject() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Klarna").build();

        // when
        when(projectRepository.save(project)).thenReturn(project);

        // then
        assertThat(projectService.insert(project)).isSameAs(project);
    }

    @Test
    void canUpdateAProject() {

        // given
        ProjectEntity projectToUpdate = ProjectEntity.builder().id(1L).name("Klarna").build();
        ProjectEntity projectNewValues = ProjectEntity.builder().id(1L).name("Greyhound").build();
        ProjectEntity updatedProject = ProjectEntity.builder().id(1L).name("Greyhound").build();

        // when
        when(projectRepository.findById(projectToUpdate.getId())).thenReturn(Optional.of(projectToUpdate));

        // then
        assertThat(projectService.update(projectToUpdate.getId(), projectNewValues)).isEqualTo(updatedProject);
    }

    @Test
    void throwsNoSuchElementExceptionWhenCantFindProjectToUpdate() {

        // given
        ProjectEntity projectToUpdate = ProjectEntity.builder().id(1L).name("Klarna").build();


        // when
        when(projectRepository.findById(projectToUpdate.getId())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> {
            projectService.update(projectToUpdate.getId(), projectToUpdate);
        }).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    void canDeleteAnProjectById() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Peroni Project").build();

        // when
        doNothing().when(projectRepository).deleteById(project.getId());
        projectService.deleteById(project.getId());

        // then
        verify(projectRepository, atLeastOnce()).deleteById(project.getId());
    }

    @Test
    void canFindAnEmployeeById() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Peroni Project").build();


        // when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));

        // then
        assertThat(projectRepository.findById(project.getId())).hasValue(project);
    }


    @Test
    void returnsEmptyWhenCantFindAnEmployeeById() {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Peroni Project").build();

        // when
        when(projectRepository.findById(project.getId())).thenReturn(Optional.empty());

        // then
        assertThat(projectService.findById(project.getId())).isEmpty();
    }

}