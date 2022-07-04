package com.example.employees.service;

import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import com.example.employees.repo.EmployeeRepository;
import com.example.employees.repo.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;
    private final EmployeeRepository employeeRepository;

    public List<String> findDistinctTitles(String order) {

        if (order == null || order.equalsIgnoreCase("asc")) {
            return titleRepository.findAllTitles().stream()
                    .sorted(Comparator.naturalOrder())
                    .toList();
        }

        if (order.equalsIgnoreCase("desc")) {
            return titleRepository.findAllTitles().stream()
                    .sorted(Comparator.reverseOrder())
                    .toList();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Page<Employee> getEmployeesWithFilteringAndOrder(
            String title,
            Integer page,
            Integer pageSize,
            String orderBy,
            Gender gender,
            LocalDate hireDateBefore,
            LocalDate hireDateAfter,
            Sort.Direction sortDirection) {

        if (hireDateBefore != null) {
            return employeeRepository.findEmployeesByTitleSortFilterBeforeHireDate(
                    title,
                    gender,
                    hireDateBefore,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        } else if (hireDateAfter != null) {
            return employeeRepository.findEmployeesByTitleSortFilterAfterAfterHireDate(
                    title,
                    gender,
                    hireDateAfter,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        } else {
            return employeeRepository.findEmployeesByTitleSortFilter(
                    title,
                    gender,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        }


    }

}
