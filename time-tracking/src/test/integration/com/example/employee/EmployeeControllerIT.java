package com.example.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = EmployeeController.class)
@AutoConfigureMockMvc
@Tag("integration")
class EmployeeControllerIT {


    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void getAll() throws Exception {
        // given
        List<EmployeeEntity> employees = new ArrayList<>(){{
            add(EmployeeEntity.builder().id(1L).build());
            add(EmployeeEntity.builder().id(2L).build());
            add(EmployeeEntity.builder().id(3L).build());
        }};
        when(employeeService.fetchAll()).thenReturn(employees);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                get("/employee/").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                objectMapper.writeValueAsString(employees));
    }

    @Test
    void addEmployee() throws Exception {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L).name("Petru").surname("Tarna").email("petru@google.com").build();
        String employeeJson = objectMapper.writeValueAsString(employee);

        when(employeeService.insert(employee)).thenReturn(employee);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        post("/employee/")
                                .content(employeeJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(employeeJson);
    }

    @Test
    void updateEmployee() throws Exception {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L).name("Petru").surname("Tarna").email("petru@google.com").build();
        String employeeJson = objectMapper.writeValueAsString(employee);

        when(employeeService.update(any(), any())).thenReturn(employee);

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        put("/employee/{employee_id}", employee.getId())
                                .content(employeeJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(employeeJson);
    }

    @Test
    void deleteEmployee() throws Exception {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .id(1L).build();

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        delete("/employee/{employee_id}", employee.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}