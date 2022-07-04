package com.example.employees.service;

import com.example.employees.controller.DepartmentController;
import com.example.employees.model.Department;
import com.example.employees.model.DepartmentEmployees;
import com.example.employees.model.Gender;
import com.example.employees.model.employee.Employee;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.repo.DepartmentEmployeeRepository;
import com.example.employees.repo.DepartmentRepository;
import com.example.employees.repo.EmployeeRepository;
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
class DepartmentServiceTest {

    @Autowired
    DepartmentController departmentController;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentEmployeeRepository departmentEmployeeRepository;

    @AfterEach
    void clearDatabases() {
        employeeRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentEmployeeRepository.deleteAll();
    }

    @Test
    void shouldGetAllDepartmentNames() {
        departmentRepository.save(new Department(1, "dep1"));
        departmentRepository.save(new Department(2, "dep2"));

        List<Department> departments = departmentRepository.findAll();
        Assertions.assertEquals(2, departments.size());

    }

    @Test
    void shouldGetEmployeesByDeptName() {
        departmentRepository.save(new Department(1, "dep1"));
        departmentRepository.save(new Department(2, "dep2"));

        departmentEmployeeRepository.save(new DepartmentEmployees(1, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(2, 2, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));

        employeeRepository.save(new Employee(1, LocalDate.parse("1950-01-01"), "John", "Doe", Gender.M, LocalDate.parse("2001-01-01")));
        employeeRepository.save(new Employee(2, LocalDate.parse("1950-01-01"), "John1", "Doe1", Gender.M, LocalDate.parse("2002-01-01")));

        Page<EmployeeDto> dep1Employees = departmentController.getEmployeesByDeptName("dep1", 0, 10, "lastName", Gender.M, Sort.Direction.DESC, null, null);
        Page<EmployeeDto> dep2Employees = departmentController.getEmployeesByDeptName("dep2", 0, 10, "lastName", Gender.M, Sort.Direction.DESC, null, null);

        Assertions.assertEquals(1, dep1Employees.getContent().size());
        Assertions.assertEquals(1, dep2Employees.getContent().size());

    }

    @Test
    void shouldGetEmployeesByGender() {
        departmentRepository.save(new Department(1, "dep1"));

        departmentEmployeeRepository.save(new DepartmentEmployees(1, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(2, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(3, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));

        employeeRepository.save(new Employee(1, LocalDate.parse("1950-01-01"), "John", "Doe", Gender.M, LocalDate.parse("2001-01-01")));
        employeeRepository.save(new Employee(2, LocalDate.parse("1950-01-01"), "Jane", "Doe1", Gender.F, LocalDate.parse("2002-01-01")));
        employeeRepository.save(new Employee(3, LocalDate.parse("1950-01-01"), "Jane2", "Doe2", Gender.F, LocalDate.parse("2002-01-01")));

        Page<EmployeeDto> dep1EmployeesFemale = departmentController.getEmployeesByDeptName("dep1", 0, 10, "lastName", Gender.F, Sort.Direction.DESC, null, null);

        Assertions.assertEquals(2, dep1EmployeesFemale.getContent().size());
    }

    @Test
    void shouldGetByHireDateBeforeOrAfter() {
        departmentRepository.save(new Department(1, "dep1"));

        departmentEmployeeRepository.save(new DepartmentEmployees(1, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(2, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(3, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));

        employeeRepository.save(new Employee(1, LocalDate.parse("1950-01-01"), "John", "Doe", Gender.F, LocalDate.parse("2001-01-01")));
        employeeRepository.save(new Employee(2, LocalDate.parse("1950-01-01"), "Jane", "Doe1", Gender.F, LocalDate.parse("2002-01-01")));
        employeeRepository.save(new Employee(3, LocalDate.parse("1950-01-01"), "Jane2", "Doe2", Gender.F, LocalDate.parse("2003-01-01")));

        LocalDate hireDate = LocalDate.parse("2001-02-02");

        Page<EmployeeDto> dep1EmployeesBeforeHireDate = departmentController.getEmployeesByDeptName("dep1", 0, 10, "lastName", Gender.F, Sort.Direction.DESC, null, hireDate);
        Page<EmployeeDto> dep1EmployeesAfterHireDate = departmentController.getEmployeesByDeptName("dep1", 0, 10, "lastName", Gender.F, Sort.Direction.DESC, hireDate, null);

        Assertions.assertEquals(2, dep1EmployeesBeforeHireDate.getContent().size());
        Assertions.assertEquals(1, dep1EmployeesAfterHireDate.getContent().size());
    }

    @Test
    void shouldSortByFirstNameDescAndAsc() {
        departmentRepository.save(new Department(1, "dep1"));

        departmentEmployeeRepository.save(new DepartmentEmployees(1, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(2, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(3, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));

        employeeRepository.save(new Employee(1, LocalDate.parse("1950-01-01"), "CJohn", "Doe", Gender.F, LocalDate.parse("2001-01-01")));
        employeeRepository.save(new Employee(2, LocalDate.parse("1950-01-01"), "DJane", "Doe1", Gender.F, LocalDate.parse("2002-01-01")));
        employeeRepository.save(new Employee(3, LocalDate.parse("1950-01-01"), "AJane2", "Doe2", Gender.F, LocalDate.parse("2003-01-01")));

        Page<EmployeeDto> dep1Employees = departmentController.getEmployeesByDeptName("dep1", 0, 10, "firstName", Gender.F, Sort.Direction.DESC, null, null);
        Page<EmployeeDto> dep1EmployeesAsc = departmentController.getEmployeesByDeptName("dep1", 0, 10, "firstName", Gender.F, Sort.Direction.ASC, null, null);
        Page<EmployeeDto> dep1EmployeesDesc = departmentController.getEmployeesByDeptName("dep1", 0, 10, "firstName", Gender.F, Sort.Direction.DESC, null, null);

        Assertions.assertEquals(3, dep1Employees.getContent().size());

        Assertions.assertEquals("AJane2", dep1EmployeesAsc.getContent().get(0).getFirstName());
        Assertions.assertEquals("CJohn", dep1EmployeesAsc.getContent().get(1).getFirstName());
        Assertions.assertEquals("DJane", dep1EmployeesAsc.getContent().get(2).getFirstName());

        Assertions.assertEquals("DJane", dep1EmployeesDesc.getContent().get(0).getFirstName());
        Assertions.assertEquals("CJohn", dep1EmployeesDesc.getContent().get(1).getFirstName());
        Assertions.assertEquals("AJane2", dep1EmployeesDesc.getContent().get(2).getFirstName());

    }

    @Test
    void shouldSortByHireDateAscOrDesc() {
        departmentRepository.save(new Department(1, "dep1"));

        departmentEmployeeRepository.save(new DepartmentEmployees(1, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(2, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));
        departmentEmployeeRepository.save(new DepartmentEmployees(3, 1, LocalDate.parse("2000-01-01"), LocalDate.parse("2020-01-01")));

        employeeRepository.save(new Employee(1, LocalDate.parse("1950-01-01"), "CJohn", "Doe", Gender.F, LocalDate.parse("2001-01-01")));
        employeeRepository.save(new Employee(2, LocalDate.parse("1950-01-01"), "DJane", "Doe1", Gender.F, LocalDate.parse("2002-01-01")));
        employeeRepository.save(new Employee(3, LocalDate.parse("1950-01-01"), "AJane2", "Doe2", Gender.F, LocalDate.parse("2003-01-01")));

        Page<EmployeeDto> dep1Employees = departmentController.getEmployeesByDeptName("dep1", 0, 10, "hireDate", Gender.F, Sort.Direction.DESC, null, null);
        Page<EmployeeDto> dep1EmployeesAsc = departmentController.getEmployeesByDeptName("dep1", 0, 10, "hireDate", Gender.F, Sort.Direction.ASC, null, null);
        Page<EmployeeDto> dep1EmployeesDesc = departmentController.getEmployeesByDeptName("dep1", 0, 10, "hireDate", Gender.F, Sort.Direction.DESC, null, null);

        Assertions.assertEquals(3, dep1Employees.getContent().size());

        Assertions.assertEquals(LocalDate.parse("2001-01-01"), dep1EmployeesAsc.getContent().get(0).getHireDate());
        Assertions.assertEquals(LocalDate.parse("2002-01-01"), dep1EmployeesAsc.getContent().get(1).getHireDate());
        Assertions.assertEquals(LocalDate.parse("2003-01-01"), dep1EmployeesAsc.getContent().get(2).getHireDate());

        Assertions.assertEquals(LocalDate.parse("2003-01-01"), dep1EmployeesDesc.getContent().get(0).getHireDate());
        Assertions.assertEquals(LocalDate.parse("2002-01-01"), dep1EmployeesDesc.getContent().get(1).getHireDate());
        Assertions.assertEquals(LocalDate.parse("2001-01-01"), dep1EmployeesDesc.getContent().get(2).getHireDate());

    }
}