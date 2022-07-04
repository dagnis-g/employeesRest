package com.example.employees.service;

import com.example.employees.model.Gender;
import com.example.employees.model.employee.Employee;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.repo.DepartmentRepository;
import com.example.employees.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<String> getDepartmentNames(String sort) {
        return departmentRepository
                .getDepartmentNames()
                .stream()
                .sorted(sort.equalsIgnoreCase("desc") ? Comparator.reverseOrder() : Comparator.naturalOrder())
                .toList();

    }

    public Page<EmployeeDto> getEmployeesByDeptName(String deptName,
                                                    Integer page,
                                                    Integer pageSize,
                                                    String orderBy,
                                                    Gender gender,
                                                    Sort.Direction sortDirection,
                                                    LocalDate hireDateBefore,
                                                    LocalDate hireDateAfter) {
        Page<Employee> employees;
        
        if (hireDateBefore != null) {
            employees = employeeRepository.findEmployeesByDeptNameAndHireDateBefore(
                    deptName, gender, hireDateBefore,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        } else if (hireDateAfter != null) {
            employees = employeeRepository.findEmployeesByDeptNameAndHireDateAfter(
                    deptName, gender, hireDateAfter,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        } else {
            employees = employeeRepository.findEmployeesByDepartment(
                    deptName, gender,
                    PageRequest.of(page, pageSize, sortDirection, orderBy));
        }

        return employees.map(employee -> modelMapper.map(employee, EmployeeDto.class));
    }

}