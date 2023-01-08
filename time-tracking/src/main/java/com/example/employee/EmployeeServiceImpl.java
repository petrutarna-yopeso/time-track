package com.example.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public  Iterable<EmployeeEntity> fetchAll() {
        return employeeRepository.findAll();
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
    public EmployeeEntity update(Long id, EmployeeEntity employee) {
        EmployeeEntity existingEmployee = employeeRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(employee, existingEmployee, "id");
        return existingEmployee;
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<EmployeeEntity> findById(Long id) {

        return employeeRepository.findById(id);
    }

    @Override
    public Optional<EmployeeEntity> findByEmail(String email) {
        return employeeRepository.findFirstByEmail(email);
    }
}
