package com.example.timetraking;

import java.util.List;


public interface TimeTrakingService {
     List<TrackEntity> fetchAll();

     TrackEntity getById(Long id);

     TrackEntity insert(TrackEntity employee);

     void deleteById(Long id);

     TrackEntity update(Long id, TrackEntity updatedEntity);
}
