package com.example.timetraking.service;

import com.example.timetraking.model.TrackEntity;
import com.example.timetraking.repository.TimeTrakingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
class TimeTrackingServiceImpl implements TimeTrakingService {

    private final TimeTrakingRepository timeTrakingRepository;

    @Override
    public List<TrackEntity> fetchAll() {
        return timeTrakingRepository.findAll();
    }

    @Override
    public Optional<TrackEntity> getById(Long id) {
        return timeTrakingRepository.findById(id);
    }

    @Override
    public TrackEntity insert(TrackEntity track) {
        return timeTrakingRepository.save(track);
    }

    @Override
    public void deleteById(Long id) {
        timeTrakingRepository.deleteById(id);
    }
}
