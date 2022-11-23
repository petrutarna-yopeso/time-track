package com.example.timetraking;

import com.example.employee.EmployeeEntity;
import com.example.project.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "time_trackings")
class TrackEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date day;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull
    private LocalTime duration;

    @NotBlank
    private String description;

    @OneToOne
    private EmployeeEntity employee;

    @OneToOne
    private ProjectEntity project;
}
