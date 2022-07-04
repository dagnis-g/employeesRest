package com.example.employees.service;

import com.example.employees.controller.EmployeeController;
import com.example.employees.controller.EmployeesController;
import com.example.employees.model.Gender;
import com.example.employees.model.employee.Employee;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.repo.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeesServiceTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeController employeeController;

    @Autowired
    EmployeesController employeesController;

    @BeforeAll
    void addEmployeesToDatabase() {
        EmployeeDto employee1 = new EmployeeDto(
                LocalDate.parse("1995-01-01"),
                "Jane",
                "Anakin",
                Gender.F,
                LocalDate.parse("2005-01-01"));
        EmployeeDto employee2 = new EmployeeDto(
                LocalDate.parse("1995-02-01"),
                "Durin",
                "Bobby",
                Gender.M,
                LocalDate.parse("2007-02-01"));
        EmployeeDto employee3 = new EmployeeDto(
                LocalDate.parse("1995-03-01"),
                "Anthony",
                "Carter",
                Gender.M,
                LocalDate.parse("2008-03-01"));

        employeeController.postEmployee(employee1);
        employeeController.postEmployee(employee2);
        employeeController.postEmployee(employee3);
    }

    @Test
    void shouldGetPageWithTwoEmployees() {
        Page<Employee> employeesPage = employeesController.getEmployeesPage(0, 2);
        Assertions.assertEquals(2, employeesPage.getNumberOfElements());

    }

    @Test
    void shouldOrderByFirstNameDesc() {
        Page<Employee> employeePage = employeesController.getOrderEmployeesBy(0,
                3,
                "firstName",
                "desc");

        Employee employee1 = employeePage.getContent().get(0);
        Employee employee2 = employeePage.getContent().get(1);
        Employee employee3 = employeePage.getContent().get(2);

        Assertions.assertEquals("Jane", employee1.getFirstName());
        Assertions.assertEquals("Durin", employee2.getFirstName());
        Assertions.assertEquals("Anthony", employee3.getFirstName());

    }

    @Test
    void shouldOrderByLastNameDesc() {
        Page<Employee> employeePage = employeesController.getOrderEmployeesBy(0,
                3,
                "lastName",
                "desc");

        Employee employee1 = employeePage.getContent().get(0);
        Employee employee2 = employeePage.getContent().get(1);
        Employee employee3 = employeePage.getContent().get(2);

        Assertions.assertEquals("Carter", employee1.getLastName());
        Assertions.assertEquals("Bobby", employee2.getLastName());
        Assertions.assertEquals("Anakin", employee3.getLastName());

    }

    @Test
    void shouldOrderByHireDateDesc() {
        Page<Employee> employeePage = employeesController.getOrderEmployeesBy(0,
                3,
                "hireDate",
                "desc");

        Employee employee1 = employeePage.getContent().get(0);
        Employee employee2 = employeePage.getContent().get(1);
        Employee employee3 = employeePage.getContent().get(2);

        Assertions.assertTrue(employee1.getHireDate().isAfter(employee2.getHireDate()));
        Assertions.assertTrue(employee2.getHireDate().isAfter(employee3.getHireDate()));

    }

    @Test
    void shouldFilterByGender() {
        Page<Employee> filteredPage = employeesController.filterEmployeesBy(0, 2, Gender.F);
        Employee filteredEmployee = filteredPage.getContent().get(0);

        Assertions.assertEquals(1, filteredPage.getNumberOfElements());
        Assertions.assertEquals(Gender.F, filteredEmployee.getGender());

    }

    @Test
    void shouldFilterByHireDateAfter() {
        String dateString = "2006-02-01";

        Page<Employee> filteredPage = employeesController.filterByHireDateBeforeOrAfter(
                0,
                3,
                dateString,
                "after");

        Employee employee1 = filteredPage.getContent().get(0);
        Employee employee2 = filteredPage.getContent().get(1);

        Assertions.assertTrue(employee1.getHireDate().isAfter(LocalDate.parse(dateString)));
        Assertions.assertTrue(employee2.getHireDate().isAfter(LocalDate.parse(dateString)));
        Assertions.assertEquals(2, filteredPage.getNumberOfElements());

    }

    @Test
    void shouldFilterByHireDateBefore() {
        String dateString = "2006-02-01";

        Page<Employee> filteredPage = employeesController.filterByHireDateBeforeOrAfter(
                0,
                3,
                dateString,
                "before");

        Employee employee1 = filteredPage.getContent().get(0);

        Assertions.assertTrue(employee1.getHireDate().isBefore(LocalDate.parse(dateString)));
        Assertions.assertEquals(1, filteredPage.getNumberOfElements());

    }
}