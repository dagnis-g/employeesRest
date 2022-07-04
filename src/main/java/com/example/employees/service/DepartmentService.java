package com.example.employees.service;

import com.example.employees.repo.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<String> getDepartmentNames(String sort) {
        return departmentRepository
                .getDepartmentNames()
                .stream()
                .sorted(sort.equalsIgnoreCase("desc") ? Comparator.reverseOrder() : Comparator.naturalOrder())
                .toList();

    }
}