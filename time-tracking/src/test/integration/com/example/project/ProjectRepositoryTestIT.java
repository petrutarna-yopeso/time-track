package com.example.project;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectRepositoryTestIT {

    @Autowired
    private ProjectRepository projectRepository;


    @Test
    public void canCreateNewProject(){
        // given
        ProjectEntity project = ProjectEntity.builder()
                .name("NitroStar")
                .build();
        // when
        ProjectEntity newProject = projectRepository.save(project);

        // then
        assertThat(project.getName()).isEqualTo(newProject.getName());
    }

}
