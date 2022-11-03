package com.example.timetraking.repository;

import com.example.timetraking.model.TrackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeTrakingRepository extends CrudRepository<TrackEntity, Long> {

    public List<TrackEntity> findAll();
}
