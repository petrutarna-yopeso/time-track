package com.example.employee.service;

import com.example.employee.model.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Iterable<EmployeeEntity> fetchAll();

    Optional<EmployeeEntity> getById(Long id);

    EmployeeEntity insert(EmployeeEntity employee);

    void deleteById(Long id);
}
