package com.example.employees.service;

import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import com.example.employees.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmployeesService {

    private final EmployeeRepository employeeRepository;

    public Page<Employee> getEmployeesPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }


    public Page<Employee> filterEmployeesByGender(
            Integer page,
            Integer pageSize,
            Gender gender) {

        return employeeRepository.findEmployeeByGender(gender, PageRequest.of(page, pageSize));
    }

    public Page<Employee> filterByHireDateBeforeOrAfter(Integer page,
                                                        Integer pageSize,
                                                        LocalDate date,
                                                        String when) {

        if (when.equalsIgnoreCase("after")) {
            return employeeRepository.findEmployeeByHireDateAfter(date, PageRequest.of(page, pageSize));
        } else if (when.equalsIgnoreCase("before")) {
            return employeeRepository.findEmployeeByHireDateBefore(date, PageRequest.of(page, pageSize));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Page<Employee> orderEmployeesBy(
            Integer page,
            Integer pageSize,
            String orderBy,
            String order) {

        if (order == null || order.equalsIgnoreCase("asc")) {
            return employeeRepository.findAll(PageRequest.of(page, pageSize, Sort.by(orderBy).ascending()));
        }

        if (order.equalsIgnoreCase("desc")) {
            return employeeRepository.findAll(PageRequest.of(page, pageSize, Sort.by(orderBy).descending()));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
