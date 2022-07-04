package com.example.employees.controller;

import com.example.employees.model.Gender;
import com.example.employees.model.employee.Employee;
import com.example.employees.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping("employees")
    public Page<Employee> getEmployeesPage(
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {

        return employeesService.getEmployeesPage(page, pageSize);
    }

    @GetMapping("employeesOrder")
    public Page<Employee> getOrderEmployeesBy(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam(defaultValue = "lastName", required = false) String orderBy,
            @RequestParam(required = false) String order) {

        return employeesService.orderEmployeesBy(page, pageSize, orderBy, order);
    }

    @GetMapping("employeesByGender")
    public Page<Employee> filterEmployeesBy(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam Gender gender) {

        return employeesService.filterEmployeesByGender(page, pageSize, gender);
    }

    @GetMapping("employeesByHireDate")
    public Page<Employee> filterByHireDateBeforeOrAfter(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam String date,
            @RequestParam String beforeOrAfter
    ) {

        return employeesService.filterByHireDateBeforeOrAfter(
                page,
                pageSize,
                LocalDate.parse(date),
                beforeOrAfter);
    }


}
