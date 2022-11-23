package com.example.timetraking;

import com.example.employee.EmployeeEntity;
import com.example.employee.EmployeeService;
import com.example.project.ProjectEntity;
import com.example.project.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
class TimeTrackingServiceImpl implements TimeTrakingService {

    private final TimeTrakingRepository timeTrakingRepository;

    private final EmployeeService employeeService;

    private final ProjectService projectService;

    @Override
    public List<TrackEntity> fetchAll() {
        return timeTrakingRepository.findAll();
    }

    @Override
    public TrackEntity getById(Long id) {
        return timeTrakingRepository.findById(id).orElseThrow();
    }

    @Override
    public TrackEntity insert(TrackEntity track) {
        EmployeeEntity employee =  employeeService.findById(track.getEmployee().getId())
                .orElseThrow(()->new NoSuchElementException(
                        String.format("Nu exista angat cu id-ul: %d", track.getEmployee().getId())));
        ProjectEntity project =  projectService.findById(track.getProject().getId()).orElseThrow(
                ()-> new NoSuchElementException(
                        String.format("Nu exista proiect cu id-ul: %d", track.getProject().getId())
        ));

        track.setEmployee(employee);
        track.setProject(project);

        return timeTrakingRepository.save(track);
    }

    @Override
    public void deleteById(Long id) {
        timeTrakingRepository.deleteById(id);
    }

    @Override
    public TrackEntity update(Long id, TrackEntity updatedEntity) {
        TrackEntity existingEntity = timeTrakingRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(updatedEntity, existingEntity, "id");
        return timeTrakingRepository.save(existingEntity);
    }
}
