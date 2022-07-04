package com.example.employees.controller;

import com.example.employees.model.Gender;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("departments")
    public List<String> getDepartmentNames(
            @RequestParam(defaultValue = "asc") String sort) {
        return departmentService.getDepartmentNames(sort);
    }

    @GetMapping("departments/{department}/employees")
    public Page<EmployeeDto> getEmployeesByDeptName(
            @PathVariable String department,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastName") String orderBy,
            @RequestParam(required = false) Gender gender,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hireDateBefore,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hireDateAfter) {
        return departmentService.getEmployeesByDeptName(department,
                page,
                pageSize,
                orderBy,
                gender,
                sortDirection,
                hireDateBefore, hireDateAfter);
    }
}
