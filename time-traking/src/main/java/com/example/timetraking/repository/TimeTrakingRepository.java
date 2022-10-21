package com.example.timetraking.repository;

import com.example.project.model.ProjectEntity;
import com.example.timetraking.model.TrackEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TimeTrakingRepository {

    private Long trackId = 0L;
    private List<TrackEntity> tracks = new ArrayList<TrackEntity>(){{
        add(TrackEntity.builder().Id(trackId++).build());
        add(TrackEntity.builder().Id(trackId++).build());
    }};

    public Iterable<TrackEntity> getAll() {
        return tracks;
    }

    public Optional<TrackEntity> findById(Long id) {
        return tracks.stream().filter(c -> c.getId().equals(id)).findAny();
    }

    public TrackEntity save (TrackEntity newTrack) {
        newTrack.setId(trackId++);
        this.tracks.add(newTrack);
        return newTrack;
    }

    public void deleteById (Long id) {
        Assert.notNull(id, "The given id must not be null!");
        tracks.remove(this.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", id), 1);
        }));
    }
}
