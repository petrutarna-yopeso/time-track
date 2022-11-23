package com.example.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Tag("integration")
public class EmployeeIntegrationTestIT {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void canAddEmployeeinDB() throws Exception {
        // given
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Petru").surname("Tarna").email("petru@google.com").build();
        String employeeJson =  objectMapper.writeValueAsString(employee);

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
