package com.example.employee.service;

import com.example.employee.model.EmployeeEntity;
import com.example.employee.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public  Iterable<EmployeeEntity> fetchAll() {
        return employeeRepository.getAll();
    }

    @Override
    public Optional<EmployeeEntity> getById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public EmployeeEntity insert(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    };
}
