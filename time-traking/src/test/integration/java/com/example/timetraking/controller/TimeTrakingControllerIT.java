package com.example.timetraking.controller;

import com.example.timetraking.model.TrackEntity;
import com.example.timetraking.service.TimeTrakingService;
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
@WebMvcTest(controllers = TimeTrakingController.class)
@AutoConfigureMockMvc
class TimeTrakingControllerIT {

    @MockBean
    private TimeTrakingService timeTrakingService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<TrackEntity> jsonTrackEntity;
    @Autowired
    private JacksonTester<List<TrackEntity>> jsonTrackEntitytList;

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
                        get("/traking/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonTrackEntitytList.write(trackEntities).getJson());
    }

    @Test
    void getDetails() throws Exception {
        // given
        TrackEntity trackEntity = TrackEntity.builder().id(3L).build();
        when(timeTrakingService.getById(trackEntity.getId())).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        get("/traking/{tracking_id}", trackEntity.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonTrackEntity.write(trackEntity).getJson());
    }

    @Test
    void addTimeTrack() throws Exception {
        // given
        TrackEntity trackEntity = TrackEntity.builder().id(3L).description("Desc").build();
        String timeTrackJson = jsonTrackEntity.write(trackEntity).getJson();

        when(timeTrakingService.insert(trackEntity)).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        post("/traking/")
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
        TrackEntity trackEntity = TrackEntity.builder().id(3L).description("Desc").build();
        String timeTrackJson = jsonTrackEntity.write(trackEntity).getJson();

        when(timeTrakingService.update(any(), any())).thenReturn(trackEntity);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        put("/traking/{tracking_id}", trackEntity.getId())
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
                        delete("/traking/{tracking_id}", trackEntity.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}