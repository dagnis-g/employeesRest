package com.example.employees.controller;

import com.example.employees.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("departments/")
    public List<String> getDepartmentNames(
            @RequestParam(defaultValue = "asc") String sort) {
        return departmentService.getDepartmentNames(sort);
    }

}
