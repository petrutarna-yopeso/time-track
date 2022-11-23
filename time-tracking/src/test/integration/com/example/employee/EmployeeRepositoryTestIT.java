package com.example.employee;

import com.example.employee.EmployeeRepository;
import com.example.employee.EmployeeEntity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest
@ActiveProfiles("test")
class EmployeeRepositoryTestIT {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void canCreateNewEmployee(){
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Petru")
                .surname("Tarna")
                .email("mail@google.com")
                .build();
        // when
        EmployeeEntity newEmployee = employeeRepository.save(employee);

        // then
        assertThat(employee.getName()).isEqualTo(newEmployee.getName());
    }

}