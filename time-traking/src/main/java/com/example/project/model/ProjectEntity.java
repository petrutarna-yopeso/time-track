package com.example.project.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Builder
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    private String name;
}
