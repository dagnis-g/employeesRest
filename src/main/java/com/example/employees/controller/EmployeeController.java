package com.example.employees.controller;

import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("employee/{employeeNo}")
    public EmployeeDto getEmployeeByNo(@PathVariable Integer employeeNo) {
        return employeeService.findEmployeeByNo(employeeNo);
    }

    @DeleteMapping("employee/{employeeNo}")
    public String deleteEmployeeByNo(@PathVariable Integer employeeNo) {
        employeeService.deleteEmployeeByNo(employeeNo);
        return "Deleted employee with No. " + employeeNo;
    }

    @PostMapping("employee")
    public EmployeeDto postEmployee(@RequestBody @Valid EmployeeDto employee) {
        return employeeService.addEmployee(employee);
    }

}
