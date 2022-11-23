package com.example.employee.service;

import com.example.employee.EmployeeServiceImpl;
import com.example.employee.EmployeeEntity;
import com.example.employee.EmployeeRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unitTest")
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void canFetchAll() {
        // given
        List<EmployeeEntity> employees = new ArrayList<>(){{
            add(EmployeeEntity.builder().id(1L).build());
            add(EmployeeEntity.builder().id(2L).build());
        }};

        // when
        when(employeeRepository.findAll()).thenReturn(employees);

        // then
        assertThat(employeeService.fetchAll()).hasSameElementsAs(employees);
    }


    @Test
    void canGetAEmployeeById() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).build();

        // when
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        // then
        assertThat(employeeService.getById(employee.getId())).hasValue(employee);
    }


    @Test
    void returnsEmptyWhenCantGetAEmployeeById() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).build();

        // when
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        // then
        assertThat(employeeService.getById(employee.getId())).isEmpty();
    }

    @Test
    void canInsertAEmployee() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L)
                .name("Petru")
                .surname("Tarna")
                .build();

        // when
        when(employeeRepository.save(employee)).thenReturn(employee);

        // then
        assertThat(employeeService.insert(employee)).isSameAs(employee);
    }

    @Test
    void canUpdateAEmployee() {

        // given
        EmployeeEntity employeeToUpdate = EmployeeEntity.builder().id(1L).name("Petru").build();
        EmployeeEntity employeeNewValues = EmployeeEntity.builder().id(3L).name("Ion").build();
        EmployeeEntity updatedEmployee = EmployeeEntity.builder().id(1L).name("Ion").build();

        // when
        when(employeeRepository.findById(employeeToUpdate.getId())).thenReturn(Optional.of(employeeToUpdate));

        // then
        assertThat(employeeService.update(employeeToUpdate.getId(), employeeNewValues)).isEqualTo(updatedEmployee);
    }

    @Test
    void throwsNoSuchElementExceptionWhenCantFindTrackingToUpdate() {

        // given
        EmployeeEntity employeeToUpdate = EmployeeEntity.builder().id(1L).name("Petru").build();

        // when
        when(employeeRepository.findById(employeeToUpdate.getId())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> {
            employeeService.update(employeeToUpdate.getId(), employeeToUpdate);
        }).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    void canDeleteAnEmployeeById() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).name("Petru").build();

        // when
        doNothing().when(employeeRepository).deleteById(employee.getId());
        employeeService.deleteById(employee.getId());

        // then
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }

    @Test
    void canFindAnEmployeeById() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).build();

        // when
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        // then
        assertThat(employeeService.findById(employee.getId())).hasValue(employee);
    }


    @Test
    void returnsEmptyWhenCantFindAnEmployeeById() {
        // given
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).build();

        // when
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.empty());

        // then
        assertThat(employeeService.findById(employee.getId())).isEmpty();
    }

}