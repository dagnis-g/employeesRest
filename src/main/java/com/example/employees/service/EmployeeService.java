package com.example.employees.service;

import com.example.employees.model.employee.Employee;
import com.example.employees.model.employee.EmployeeDto;
import com.example.employees.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeDto findEmployeeByNo(Integer no) {
        Optional<Employee> employeeToFind = employeeRepository.findById(no);

        if (employeeToFind.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return modelMapper.map(employeeToFind, EmployeeDto.class);
    }

    public void deleteEmployeeByNo(Integer no) {
        try {
            employeeRepository.deleteById(no);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public EmployeeDto addEmployee(EmployeeDto employee) {
        Employee employeeToAdd = modelMapper.map(employee, Employee.class);
        employeeToAdd.setEmployeeNo(setId());

        if (checkIfUserInRepo(employeeToAdd)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already in database");
        }

        Employee addedEmployee = employeeRepository.save(employeeToAdd);
        return modelMapper.map(addedEmployee, EmployeeDto.class);

    }

    private boolean checkIfUserInRepo(Employee employee) {
        return employeeRepository.existsByBirthDateAndFirstNameAndLastNameAndGender(
                employee.getBirthDate()
                , employee.getFirstName()
                , employee.getLastName()
                , employee.getGender());
    }

    private Integer setId() {
        try {
            return employeeRepository.findTopByOrderByEmployeeNoDesc().getEmployeeNo() + 1;
        } catch (NullPointerException e) {
            return 1;
        }

    }
}
