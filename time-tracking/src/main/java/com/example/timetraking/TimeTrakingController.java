package com.example.timetraking;

import com.example.anotations.Authorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/tracking")
@RequiredArgsConstructor
@Slf4j
class TimeTrakingController {

    private final TimeTrakingService timeTrakingService;

    @GetMapping
    public ResponseEntity<List<TrackEntity>> getAll() {
        return (ResponseEntity.ok(timeTrakingService.fetchAll()));
    }

    @GetMapping("/{tracking_id}")
    @Authorize
    public ResponseEntity<TrackEntity> getDetails(@PathVariable(name = "tracking_id") Long id) {
        return (ResponseEntity.ok(timeTrakingService.getById(id)));
    }

    @PostMapping
    @Authorize
    public ResponseEntity<TrackEntity> addTimeTrack(@Valid @RequestBody TrackEntity newTimeTrack) {
        return ResponseEntity.ok(timeTrakingService.insert(newTimeTrack));
    }

    @PutMapping("/{tracking_id}")
    @Authorize
    public ResponseEntity<TrackEntity> updateTimeTrack(
            @PathVariable(name = "tracking_id") Long id,
            @Valid @RequestBody TrackEntity updatedEntity
    ) {
        return ResponseEntity.ok(timeTrakingService.update(id, updatedEntity));
    }

    @DeleteMapping("/{tracking_id}")
    @Authorize
    public ResponseEntity<?> deleteTimeTrack(@PathVariable(name = "tracking_id") Long id) {
        try {
            timeTrakingService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    };
}
