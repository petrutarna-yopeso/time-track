package com.example.timetraking.model;

import com.example.employee.model.EmployeeEntity;
import com.example.project.model.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "time_trackings")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    private Date day;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime duration;

    @NotBlank
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private EmployeeEntity employee;

    @OneToOne(fetch = FetchType.LAZY)
    private ProjectEntity project;
}
