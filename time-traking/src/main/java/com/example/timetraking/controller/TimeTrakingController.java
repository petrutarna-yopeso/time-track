package com.example.timetraking.controller;

import com.example.timetraking.model.TrackEntity;
import com.example.timetraking.service.TimeTrakingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/traking"})
@RequiredArgsConstructor
@Slf4j
public class TimeTrakingController {

    private final TimeTrakingService timeTrakingService;

    @GetMapping("/")
    public ResponseEntity<List<TrackEntity>> getAll() {
        return (ResponseEntity.ok(timeTrakingService.fetchAll()));
    }

    @PostMapping("/")
    public ResponseEntity<TrackEntity> addTimeTrack(@Valid @RequestBody TrackEntity newTimeTrack) {
        return ResponseEntity.ok(timeTrakingService.insert(newTimeTrack));
    }

    @DeleteMapping("/{tracking_id}")
    public ResponseEntity<?> deleteTimeTrack(@PathVariable(name = "tracking_id") Long id) {
        try {
            timeTrakingService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    };
}
