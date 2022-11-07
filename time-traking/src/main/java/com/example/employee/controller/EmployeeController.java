package com.example.employee.controller;

import com.example.employee.model.EmployeeEntity;
import com.example.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee/")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return (ResponseEntity.ok(employeeService.fetchAll()));
    };

    @PostMapping
    public ResponseEntity<EmployeeEntity> addEmployee(@Valid @RequestBody EmployeeEntity newEmployee) {
        return ResponseEntity.ok(employeeService.insert(newEmployee));
    }

    @PutMapping("{employee_id}")
    public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable(name = "employee_id") Long id, @Valid @RequestBody EmployeeEntity newEmployee) {
        return ResponseEntity.ok(employeeService.update(id, newEmployee));
    }

    @DeleteMapping("{employee_id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(name = "employee_id") Long id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception ex) {
            log.info(String.format("Can't delete employee with id: %d", id), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    };
}
