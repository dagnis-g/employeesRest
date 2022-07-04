package com.example.employees.controller;

import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import com.example.employees.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TitleController {

    private final TitleService titleService;

    @GetMapping("titles/")
    public List<String> findAllTitles(@RequestParam(required = false) String order) {
        return titleService.findDistinctTitles(order);
    }

    @GetMapping("employees/titles/{title}")
    public Page<Employee> getEmployeesByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastName") String orderBy,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String hireDateBefore,
            @RequestParam(required = false) String hireDateAfter,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDirection
    ) {

        return titleService.getEmployeesWithFilteringAndOrder(title,
                page,
                pageSize,
                orderBy,
                gender,
                hireDateBefore,
                hireDateAfter,
                sortDirection);
    }

}
