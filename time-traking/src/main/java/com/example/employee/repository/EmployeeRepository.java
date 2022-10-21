package com.example.employee.repository;

import com.example.employee.model.EmployeeEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private Long employeeId = 0L;
    private List<EmployeeEntity> employees = new ArrayList<EmployeeEntity>(){{
        add(EmployeeEntity.builder().Id(employeeId++).Name("Vasile").build());
        add(EmployeeEntity.builder().Id(employeeId++).Name("Ion").build());
    }};

    public Iterable<EmployeeEntity> getAll() {
        return employees;
    }

    public Optional<EmployeeEntity> findById(Long id) {
        return employees.stream().filter(c -> c.getId().equals(id)).findAny();
    }

    public EmployeeEntity save (EmployeeEntity newEmployee) {
        newEmployee.setId(employeeId++);
        this.employees.add(newEmployee);
        return newEmployee;
    }

    public void deleteById (Long id) {
        Assert.notNull(id, "The given id must not be null!");
        employees.remove(this.findById(id).orElseThrow(() -> {
            throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", id), 1);
        }));
    }
}
