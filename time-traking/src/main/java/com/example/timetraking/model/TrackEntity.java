package com.example.timetraking.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class TrackEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    private LocalDateTime Begin;
    private LocalDateTime End;

}
