package com.example.project.controller;

import com.example.project.model.ProjectEntity;
import com.example.project.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = ProjectController.class)
@AutoConfigureMockMvc
class ProjectControllerIT {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<ProjectEntity> jsonProject;
    @Autowired
    private JacksonTester<List<ProjectEntity>> jsonProjectList;

    @Test
    void canGetAll() throws Exception {
        // given
        List<ProjectEntity> projects = new ArrayList<>(){{
            add(ProjectEntity.builder().id(1L).build());
            add(ProjectEntity.builder().id(2L).build());
            add(ProjectEntity.builder().id(3L).build());
        }};
        when(projectService.fetchAll()).thenReturn(projects);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        get("/project/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProjectList.write(projects).getJson());
    }

    @Test
    void canGetDetails() throws Exception {

        // given
        ProjectEntity project = ProjectEntity.builder().id(3L).build();
        when(projectService.getById(project.getId())).thenReturn(project);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        get("/project/{project_id}", project.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonProject.write(project).getJson());
    }

    @Test
    void canAddProject() throws Exception {

        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("Time Tracking").build();


        String projectJson = jsonProject.write(project).getJson();

        when(projectService.insert(project)).thenReturn(project);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        post("/project/")
                                .content(projectJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(projectJson);
    }

    @Test
    void canUpdateProject() throws Exception {

        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("New Project").build();
        String projectJson = jsonProject.write(project).getJson();

        when(projectService.update(any(), any())).thenReturn(project);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        put("/project/{project_id}", project.getId())
                                .content(projectJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(projectJson);
    }

    @Test
    void canDeleteProject() throws Exception {
        // given
        ProjectEntity project = ProjectEntity.builder().id(1L).name("New Project").build();

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        delete("/project/{project_id}", project.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}