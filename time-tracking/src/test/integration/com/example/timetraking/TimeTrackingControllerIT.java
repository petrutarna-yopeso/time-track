package com.example.timetraking;

import com.example.employee.EmployeeEntity;
import com.example.project.ProjectEntity;
import com.example.timetraking.TimeTrakingController;
import com.example.timetraking.TrackEntity;
import com.example.timetraking.TimeTrakingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TimeTrakingController.class)
@AutoConfigureMockMvc
@Tag("integration")
class TimeTrackingControllerIT {

    @MockBean
    private TimeTrakingService timeTrakingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void canGetAll() throws Exception {
        // given
        List<TrackEntity> trackEntities = new ArrayList<>(){{
            add(TrackEntity.builder().id(1L).build());
            add(TrackEntity.builder().id(2L).build());
            add(TrackEntity.builder().id(3L).build());
        }};
        when(timeTrakingService.fetchAll()).thenReturn(trackEntities);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        get("/tracking/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                objectMapper.writeValueAsString(trackEntities));
    }

    @Test
    void getDetails() throws Exception {
        // given
        TrackEntity trackEntity = TrackEntity.builder().id(3L).build();
        when(timeTrakingService.getById(trackEntity.getId())).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        get("/tracking/{tracking_id}", trackEntity.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo( objectMapper.writeValueAsString(trackEntity));
    }

    @Test
    void addTimeTrack() throws Exception {
        // given
        TrackEntity trackEntity = TrackEntity.builder()
                .description("Desc")
                .project(ProjectEntity.builder().build())
                .employee(EmployeeEntity.builder().build())
                .day(Date.valueOf(LocalDate.now()))
                .duration(LocalTime.parse("02:30"))
                .build();
        String timeTrackJson =  objectMapper.writeValueAsString(trackEntity);

        when(timeTrakingService.insert(any(TrackEntity.class))).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        post("/tracking/")
                                .content(timeTrackJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(timeTrackJson);
    }

    @Test
    void updateTimeTrack() throws Exception {
        // given
        TrackEntity trackEntity = TrackEntity.builder()
                .id(3L)
                .description("Desc")
                .project(ProjectEntity.builder().build())
                .employee(EmployeeEntity.builder().build())
                .day(Date.valueOf(LocalDate.now()))
                .duration(LocalTime.parse("02:30"))
                .build();
        String timeTrackJson =  objectMapper.writeValueAsString(trackEntity);

        when(timeTrakingService.update(any(Long.class), any(TrackEntity.class))).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        put("/tracking/{tracking_id}", trackEntity.getId())
                                .content(timeTrackJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(timeTrackJson);
    }

    @Test
    void canDeleteTimeTrack() throws Exception {

        // given
        TrackEntity trackEntity = TrackEntity.builder().id(1L).build();

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        delete("/tracking/{tracking_id}", trackEntity.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}