package com.example.employee.repository;

import com.example.employee.model.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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