package com.example.employees.service;

import com.example.employees.controller.EmployeeController;
import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import com.example.employees.repo.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.NoSuchElementException;

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
        Integer employeeNo = 0;
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        Employee employee = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);

        Employee employeeAdded = employeeController.postEmployee(employee);
        Integer addedEmployeeNo = employeeAdded.getEmployeeNo();

        Employee employeeActual = employeeRepository.findById(addedEmployeeNo).get();

        Assertions.assertEquals(employee, employeeActual);
    }

    @Test
    void shouldThrowIfNoEmployeeToFindByEmpNo() {
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.getEmployeeByNo(9999));
    }

    @Test
    void shouldThrowIfEmployeeAlreadyInDatabase() {
        Integer employeeNo = 0;
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        Employee employee1 = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);
        Employee employee2 = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);

        employeeController.postEmployee(employee1);
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.postEmployee(employee2));
    }

    @Test
    void shouldThrowOnEmptyName() {
        Integer employeeNo = 0;
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        Employee employee1 = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);

        Assertions.assertThrows(TransactionSystemException.class, () -> employeeController.postEmployee(employee1));
    }

    @Test
    void shouldThrowIfNoTValidBirthDate() {
        Integer employeeNo = 0;
        LocalDate birthDate = LocalDate.now().plusDays(2);
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        Employee employee1 = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);

        Assertions.assertThrows(TransactionSystemException.class, () -> employeeController.postEmployee(employee1));
    }

    @Test
    void shouldDeleteEmployeeByNo() {
        Integer employeeNo = 0;
        LocalDate birthDate = LocalDate.parse("1966-06-01");
        String firstName = "John";
        String lastName = "Doe";
        Gender gender = Gender.M;
        LocalDate hireDate = LocalDate.parse("1986-06-01");
        Employee employee = new Employee(employeeNo, birthDate, firstName, lastName, gender, hireDate);

        Employee employeeAdded = employeeController.postEmployee(employee);
        Integer addedEmployeeNo = employeeAdded.getEmployeeNo();

        String actualMessage = employeeController.deleteEmployeeByNo(addedEmployeeNo);
        String expectedMessage = "Deleted employee with ID " + addedEmployeeNo;

        Assertions.assertEquals(expectedMessage, actualMessage);
        Assertions.assertThrows(NoSuchElementException.class, () -> employeeRepository.findById(addedEmployeeNo).get());
    }

    @Test
    void shouldThrowIfNothingToDeleteByNo() {
        Assertions.assertThrows(ResponseStatusException.class, () -> employeeController.deleteEmployeeByNo(9909090));
    }


}