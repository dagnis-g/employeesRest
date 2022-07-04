package com.example.employees.service;

import com.example.employees.controller.EmployeeController;
import com.example.employees.model.Gender;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.repo.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeController employeeController;

    @AfterEach
    void clearDatabase() {
        employeeRepository.deleteAll();
    }

    @Test
    void shouldAddAndFindEmployee() {
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        EmployeeDto employeeDto = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);

        employeeController.postEmployee(employeeDto);
        Integer employeeNo = employeeRepository.findAll().get(0).getEmployeeNo();
        EmployeeDto employeeAdded = employeeController.getEmployeeByNo(employeeNo);

        Assertions.assertEquals(birthDate, employeeAdded.getBirthDate());
        Assertions.assertEquals(firstName, employeeAdded.getFirstName());
        Assertions.assertEquals(lastName, employeeAdded.getLastName());
        Assertions.assertEquals(gender, employeeAdded.getGender());
        Assertions.assertEquals(hireDate, employeeAdded.getHireDate());

    }

    @Test
    void shouldThrowIfNoEmployeeToFindByEmpNo() {
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.getEmployeeByNo(9999));
    }

    @Test
    void shouldThrowIfEmployeeAlreadyInDatabase() {
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        EmployeeDto employee1 = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);
        EmployeeDto employee2 = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);

        employeeController.postEmployee(employee1);
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.postEmployee(employee2));

    }

    @Test
    void shouldDeleteEmployeeByNo() {
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        EmployeeDto employeeDtoToAdd = new EmployeeDto(birthDate, firstName, lastName, gender, hireDate);

        employeeController.postEmployee(employeeDtoToAdd);
        Integer employeeNo = employeeRepository.findAll().get(0).getEmployeeNo();
        employeeController.deleteEmployeeByNo(employeeNo);

        Assertions.assertEquals(0, employeeRepository.findAll().size());

    }

    @Test
    void shouldThrowIfNothingToDeleteByNo() {
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.deleteEmployeeByNo(9909090));
    }


}