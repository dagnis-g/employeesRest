package com.example.employees.controller;

import com.example.employees.model.Employee;
import com.example.employees.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("employee/{employeeNo}")
    public Employee getEmployeeByNo(@PathVariable Integer employeeNo) {
        return employeeService.findEmployeeByNo(employeeNo);
    }

    @DeleteMapping("employee/{employeeNo}")
    public String deleteEmployeeByNo(@PathVariable Integer employeeNo) {
        return employeeService.deleteEmployeeByNo(employeeNo);
    }

    @PostMapping("employee/")
    public Employee postEmployee(@RequestBody @Valid Employee employee) {
        return employeeService.addEmployee(employee);
    }

}
