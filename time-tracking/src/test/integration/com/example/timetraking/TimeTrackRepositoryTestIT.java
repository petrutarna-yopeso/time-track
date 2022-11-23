package com.example.timetraking;

import com.example.employee.EmployeeEntity;
import com.example.employee.EmployeeRepository;
import com.example.project.ProjectEntity;
import com.example.project.ProjectService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TimeTrackRepositoryTestIT {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TimeTrakingRepository timeTrackingRepository;

    EmployeeEntity newEmployee;
    ProjectEntity newProject;

    @BeforeAll
    public void insertnewEmployeeAndProject() {
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Petru")
                .surname("Tarna")
                .email("mail@google.com")
                .build();
        newEmployee = employeeRepository.save(employee);

        ProjectEntity project = ProjectEntity.builder()
                .name("NitroStar")
                .build();
        newProject = projectService.insert(project);
    }

    @Test
    public void canCreateNewTrack(){
        // given
        TrackEntity track = TrackEntity.builder()
                .description("Work from home")
                .duration(LocalTime.parse("02:30"))
                .day(Date.valueOf(LocalDate.now()))
                .project(newProject)
                .employee(newEmployee)
                .build();
        // when
        TrackEntity newTrack = timeTrackingRepository.save(track);

        // then
        assertThat(track.getDescription()).isEqualTo(newTrack.getDescription());
    }

}
