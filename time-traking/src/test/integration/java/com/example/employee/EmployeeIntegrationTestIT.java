package com.example.employee;

import com.example.employee.model.EmployeeEntity;
import com.example.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeIntegrationTestIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JacksonTester<EmployeeEntity> jsonEmployee;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void canAddEmployeeinDB() throws Exception {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Petru").surname("Tarna").email("petru@google.com").build();
        String employeeJson = jsonEmployee.write(employee).getJson();

        //when
        MockHttpServletResponse response =  mockMvc.perform(
                        post("/employee/")
                                .content(employeeJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        EmployeeEntity newEmployee = objectMapper.readValue(response.getContentAsString(), EmployeeEntity.class);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(employeeService.findById(newEmployee.getId())).isNotNull();
    }
}
