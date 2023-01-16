package com.example.timetraking;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.employee.EmployeeEntity;
import com.example.employee.EmployeeService;
import com.example.project.ProjectEntity;
import com.example.project.ProjectService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unitTest")
class TimeTrakingServiceImplTest {


    @InjectMocks
    private TimeTrackingServiceImpl timeTrakingService;

    @Mock
    private TimeTrakingRepository timeTrakingRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ProjectService projectService;

    @Test
    void canFetchAllTrackings() {

        // given
        List<TrackEntity> timeTrakings = new ArrayList<>(){{
            add(TrackEntity.builder().id(1L).build());
            add(TrackEntity.builder().id(2L).build());
        }};

        // when
        when(timeTrakingRepository.findAll()).thenReturn(timeTrakings);

        // then
        assertThat(timeTrakingService.fetchAll()).hasSameElementsAs(timeTrakings);
    }

    @Test
    void canGetATrackingById() {
        // given
        TrackEntity timeTraking = TrackEntity.builder().id(1L).build();

        // when
        when(timeTrakingRepository.findById(timeTraking.getId())).thenReturn(Optional.of(timeTraking));

        // then
        assertThat(timeTrakingService.getById(timeTraking.getId())).isEqualTo(timeTraking);
    }


    @Test
    void throwsNoSuchElementExceptionWhenCantGetATrackingById() {
        // given
        TrackEntity timeTraking = TrackEntity.builder().id(1L).build();

        // when
        when(timeTrakingRepository.findById(timeTraking.getId())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> timeTrakingService.getById(timeTraking.getId())).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    void canInsertATracking() {
        // given
        TrackEntity timeTraking = TrackEntity.builder()
                .id(1L)
                .description("Desc")
                .employee(EmployeeEntity.builder().id(1L).build())
                .project(ProjectEntity.builder().id(1L).build())
                .build();

        // when
        when(timeTrakingRepository.save(timeTraking)).thenReturn(timeTraking);
        when(employeeService.findById(timeTraking.getEmployee().getId()))
                .thenReturn(Optional.ofNullable(timeTraking.getEmployee()));
        when(projectService.findById(timeTraking.getProject().getId()))
                .thenReturn(Optional.ofNullable(timeTraking.getProject()));

        // then
        assertThat(timeTrakingService.insert(timeTraking)).isSameAs(timeTraking);
    }

    @Test
    void throwsNoSuchElementExceptionWhenCantFindEmployeeOnInsertATracking() {
        // given
        TrackEntity timeTraking = TrackEntity.builder()
                .id(1L)
                .employee(EmployeeEntity.builder().id(1L).build())
                .build();

        // when
        when(employeeService.findById(timeTraking.getEmployee().getId()))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> timeTrakingService.insert(timeTraking)).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining(String.format("Nu exista angat cu id-ul: %d", timeTraking.getEmployee().getId()));
    }

    @Test
    void throwsNoSuchElementExceptionWhenCantFindProjectOnInsertATracking() {
        // given
        TrackEntity timeTraking = TrackEntity.builder()
                .id(1L)
                .project(ProjectEntity.builder().id(1L).build())
                .employee(EmployeeEntity.builder().id(1L).build())
                .build();

        // when
        when(employeeService.findById(timeTraking.getEmployee().getId()))
                .thenReturn(Optional.ofNullable(timeTraking.getEmployee()));

        when(projectService.findById(timeTraking.getProject().getId()))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> timeTrakingService.insert(timeTraking))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Nu exista proiect cu id-ul");
    }

    @Test
    void canDeleteATimeTrackById() {
        // given
        TrackEntity timeTraking = TrackEntity.builder().id(1L).build();

        // when
        doNothing().when(timeTrakingRepository).deleteById(timeTraking.getId());
        timeTrakingService.deleteById(timeTraking.getId());

        // then
        verify(timeTrakingRepository, times(1)).deleteById(timeTraking.getId());
    }

    @Test
    void canUpdateATimeTrack() {

        // given
        TrackEntity timeTrakingToUpdate = TrackEntity.builder().id(1L).description("Test Project").build();
        TrackEntity timeTrakingNewValues = TrackEntity.builder().id(3L).description("Updated Test Project").build();
        TrackEntity updatedTimeTraking = TrackEntity.builder().id(1L).description("Updated Test Project").build();

        // when
        when(timeTrakingRepository.findById(timeTrakingToUpdate.getId())).thenReturn(Optional.of(timeTrakingToUpdate));

        timeTrakingService.update(timeTrakingToUpdate.getId(), timeTrakingNewValues);

        // then
        ArgumentCaptor<TrackEntity> trackEntityArgumentCaptor =
                ArgumentCaptor.forClass(TrackEntity.class);

        verify(timeTrakingRepository).save(trackEntityArgumentCaptor.capture());

        TrackEntity captured = trackEntityArgumentCaptor.getValue();

        assertThat(captured).isEqualTo(updatedTimeTraking);
    }

    @Test
    void throwsNoSuchElementExceptionWhenCantFindTrackingToUpdate() {

        // given
        TrackEntity timeTrakingToUpdate = TrackEntity.builder().id(1L).description("Test Project").build();

        // when
        when(timeTrakingRepository.findById(timeTrakingToUpdate.getId())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> timeTrakingService.update(timeTrakingToUpdate.getId(), timeTrakingToUpdate)).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }
}