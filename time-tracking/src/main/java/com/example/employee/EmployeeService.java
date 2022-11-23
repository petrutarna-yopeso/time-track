package com.example.employee;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmployeeService {
    Iterable<EmployeeEntity> fetchAll();

    Optional<EmployeeEntity> getById(Long id);

    EmployeeEntity insert(EmployeeEntity employee);

    @Transactional
    EmployeeEntity update(Long id, EmployeeEntity employee);

    void deleteById(Long id);

    Optional<EmployeeEntity> findById(Long id);
}
