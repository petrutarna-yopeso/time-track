package com.example.timetraking.controller.employee;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/employee"})
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    @GetMapping("/")
    public ResponseEntity<?> getAll() {return (ResponseEntity<?>) ResponseEntity.ok();};
}
