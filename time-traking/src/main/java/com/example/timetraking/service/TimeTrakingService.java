package com.example.timetraking.service;

import com.example.project.model.ProjectEntity;
import com.example.timetraking.model.TrackEntity;
import com.example.timetraking.repository.TimeTrakingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TimeTrakingService {
     List<TrackEntity> fetchAll();

     Optional<TrackEntity> getById(Long id);

     TrackEntity insert(TrackEntity employee);

     void deleteById(Long id);

}
