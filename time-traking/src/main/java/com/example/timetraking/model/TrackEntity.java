package com.example.timetraking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "time_trackings")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private LocalDateTime begin;

    @NotBlank
    private LocalDateTime finish;

}
