package com.example.timetraking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface TimeTrakingRepository extends CrudRepository<TrackEntity, Long> {

    public List<TrackEntity> findAll();
}
