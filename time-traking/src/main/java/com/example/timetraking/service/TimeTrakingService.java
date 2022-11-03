package com.example.timetraking.service;

import com.example.timetraking.model.TrackEntity;

import java.util.List;
import java.util.Optional;


public interface TimeTrakingService {
     List<TrackEntity> fetchAll();

     Optional<TrackEntity> getById(Long id);

     TrackEntity insert(TrackEntity employee);

     void deleteById(Long id);

}
