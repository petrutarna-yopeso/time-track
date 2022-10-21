package com.example.timetraking.controller.tracking;

import com.example.timetraking.model.TrackEntity;
import com.example.timetraking.service.TimeTrakingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/traking"})
@RequiredArgsConstructor
@Slf4j
public class TimeTraking {

    private final TimeTrakingService timeTrakingService;

    @GetMapping("/")
    public ResponseEntity<List<TrackEntity>> getAll() {
        return (ResponseEntity<List<TrackEntity>>) timeTrakingService.fetchAll();
    }
}
