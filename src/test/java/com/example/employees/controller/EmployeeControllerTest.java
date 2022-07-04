package com.example.employees.controller;

import com.example.employees.model.Gender;
import com.example.employees.model.employee.EmployeeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldThrowIfNoValidBirthDate() throws Exception {
        LocalDate birthDate = LocalDate.now().plusDays(2);
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        EmployeeDto employee1 = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);

        this.mvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldThrowOnEmptyName() throws Exception {
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        EmployeeDto employee1 = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);

        this.mvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isBadRequest());

    }


}