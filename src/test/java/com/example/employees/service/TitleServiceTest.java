package com.example.employees.service;

import com.example.employees.controller.TitleController;
import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import com.example.employees.model.Title;
import com.example.employees.repo.EmployeeRepository;
import com.example.employees.repo.TitleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class TitleServiceTest {

    @Autowired
    TitleRepository titleRepository;

    @Autowired
    TitleController titleController;

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    void clearDatabases() {
        titleRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void shouldFindDistinctTitlesAscAndDesc() {
        String titleName1 = "Staff1";
        String titleName2 = "Staff2";

        Title title1 = new Title(1, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title2 = new Title(2, titleName2, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title3 = new Title(3, titleName2, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        titleRepository.save(title1);
        titleRepository.save(title2);
        titleRepository.save(title3);

        List<String> allTitlesAsc = titleController.findAllTitles("ASC");
        List<String> allTitlesDesc = titleController.findAllTitles("desc");
        Assertions.assertEquals(2, allTitlesAsc.size());
        Assertions.assertEquals(allTitlesAsc.get(0), titleName1);
        Assertions.assertEquals(allTitlesAsc.get(1), titleName2);

        Assertions.assertEquals(2, allTitlesDesc.size());

    }

    @Test
    void shouldFilterByGender() {
        String titleName1 = "Staff1";
        Title title1 = new Title(1, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title2 = new Title(2, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title3 = new Title(3, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        titleRepository.save(title1);
        titleRepository.save(title2);
        titleRepository.save(title3);

        Employee employee1 = new Employee(1, LocalDate.parse("1991-01-01"), "Jane", "Doe", Gender.F, LocalDate.parse("2000-01-01"));
        Employee employee2 = new Employee(2, LocalDate.parse("1992-01-01"), "Jane1", "Do1", Gender.F, LocalDate.parse("2000-01-01"));
        Employee employee3 = new Employee(3, LocalDate.parse("1993-01-01"), "Jan2", "Doe2", Gender.M, LocalDate.parse("2000-01-01"));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Page<Employee> femaleEmployees = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", Gender.F, null, null, Sort.Direction.valueOf("ASC"));
        Assertions.assertEquals(2, femaleEmployees.getNumberOfElements());
        Assertions.assertEquals(Gender.F, femaleEmployees.getContent().get(0).getGender());
        Assertions.assertEquals(Gender.F, femaleEmployees.getContent().get(1).getGender());

        Page<Employee> maleEmployees = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", Gender.M, null, null, Sort.Direction.valueOf("ASC"));
        Assertions.assertEquals(1, maleEmployees.getNumberOfElements());
        Assertions.assertEquals(Gender.M, maleEmployees.getContent().get(0).getGender());

    }

    @Test
    void shouldFilterByHireDateAfter() {
        String titleName1 = "Staff1";
        Title title1 = new Title(1, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title2 = new Title(2, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2003-01-01"));
        Title title3 = new Title(3, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2004-01-01"));
        titleRepository.save(title1);
        titleRepository.save(title2);
        titleRepository.save(title3);

        Employee employee1 = new Employee(1, LocalDate.parse("1991-01-01"), "Jane", "Doe", Gender.F, LocalDate.parse("2000-01-01"));
        Employee employee2 = new Employee(2, LocalDate.parse("1992-01-01"), "Jane1", "Do1", Gender.F, LocalDate.parse("2003-01-01"));
        Employee employee3 = new Employee(3, LocalDate.parse("1993-01-01"), "Jan2", "Doe2", Gender.M, LocalDate.parse("2004-01-01"));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        LocalDate stringDateHire = LocalDate.parse("2001-01-01");
        Page<Employee> employeesByDateAfter = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", null, null, stringDateHire, Sort.Direction.valueOf("ASC"));

        Assertions.assertEquals(2, employeesByDateAfter.getContent().size());
        Assertions.assertTrue(stringDateHire.isBefore(employeesByDateAfter.getContent().get(0).getHireDate()));
        Assertions.assertTrue(stringDateHire.isBefore(employeesByDateAfter.getContent().get(1).getHireDate()));

        Page<Employee> employeesByDateBefore = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", null, stringDateHire, null, Sort.Direction.valueOf("ASC"));

        Assertions.assertEquals(1, employeesByDateBefore.getContent().size());
        Assertions.assertTrue(stringDateHire.isAfter(employeesByDateBefore.getContent().get(0).getHireDate()));

    }

    @Test
    void shouldOrderByFirstNameAscAndDesc() {
        String titleName1 = "Staff1";
        Title title1 = new Title(1, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title2 = new Title(2, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2003-01-01"));
        Title title3 = new Title(3, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2004-01-01"));
        titleRepository.save(title1);
        titleRepository.save(title2);
        titleRepository.save(title3);

        Employee employee1 = new Employee(1, LocalDate.parse("1991-01-01"), "Anna", "Doe", Gender.F, LocalDate.parse("2000-01-01"));
        Employee employee2 = new Employee(2, LocalDate.parse("1992-01-01"), "Zane", "Do1", Gender.F, LocalDate.parse("2003-01-01"));
        Employee employee3 = new Employee(3, LocalDate.parse("1993-01-01"), "Daniel", "Doe2", Gender.M, LocalDate.parse("2004-01-01"));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Page<Employee> employeesAsc = titleController.getEmployeesByTitle(titleName1, 0, 5, "firstName", null, null, null, Sort.Direction.valueOf("ASC"));
        Assertions.assertEquals(employeesAsc.getContent().get(0).getFirstName(), "Anna");
        Assertions.assertEquals(employeesAsc.getContent().get(1).getFirstName(), "Daniel");
        Assertions.assertEquals(employeesAsc.getContent().get(2).getFirstName(), "Zane");

        Page<Employee> employeesDesc = titleController.getEmployeesByTitle(titleName1, 0, 5, "firstName", null, null, null, Sort.Direction.valueOf("DESC"));
        Assertions.assertEquals(employeesDesc.getContent().get(2).getFirstName(), "Anna");
        Assertions.assertEquals(employeesDesc.getContent().get(1).getFirstName(), "Daniel");
        Assertions.assertEquals(employeesDesc.getContent().get(0).getFirstName(), "Zane");

    }

    @Test
    void shouldOrderByHireDateAscAndDesc() {
        String titleName1 = "Staff1";
        Title title1 = new Title(1, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2001-01-01"));
        Title title2 = new Title(2, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2003-01-01"));
        Title title3 = new Title(3, titleName1, LocalDate.parse("2000-01-01"), LocalDate.parse("2004-01-01"));
        titleRepository.save(title1);
        titleRepository.save(title2);
        titleRepository.save(title3);

        Employee employee1 = new Employee(1, LocalDate.parse("1991-01-01"), "Anna", "Doe", Gender.F, LocalDate.parse("2000-01-01"));
        Employee employee2 = new Employee(2, LocalDate.parse("1992-01-01"), "Zane", "Do1", Gender.F, LocalDate.parse("2003-01-01"));
        Employee employee3 = new Employee(3, LocalDate.parse("1993-01-01"), "Daniel", "Doe2", Gender.M, LocalDate.parse("2004-01-01"));
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        Page<Employee> employeesAsc = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", null, null, null, Sort.Direction.valueOf("ASC"));
        Assertions.assertEquals(employeesAsc.getContent().get(0).getHireDate(), LocalDate.parse("2000-01-01"));
        Assertions.assertEquals(employeesAsc.getContent().get(1).getHireDate(), LocalDate.parse("2003-01-01"));
        Assertions.assertEquals(employeesAsc.getContent().get(2).getHireDate(), LocalDate.parse("2004-01-01"));

        Page<Employee> employeesDesc = titleController.getEmployeesByTitle(titleName1, 0, 5, "hireDate", null, null, null, Sort.Direction.valueOf("DESC"));
        Assertions.assertEquals(employeesDesc.getContent().get(0).getHireDate(), LocalDate.parse("2004-01-01"));
        Assertions.assertEquals(employeesDesc.getContent().get(1).getHireDate(), LocalDate.parse("2003-01-01"));
        Assertions.assertEquals(employeesDesc.getContent().get(2).getHireDate(), LocalDate.parse("2000-01-01"));
    }

}