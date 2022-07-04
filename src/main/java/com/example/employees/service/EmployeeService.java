package com.example.employees.service;

import com.example.employees.model.Employee;
import com.example.employees.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee findEmployeeByNo(Integer no) {
        return employeeRepository.findById(no).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String deleteEmployeeByNo(Integer no) {
        try {
            employeeRepository.deleteById(no);
            return "Deleted employee with ID " + no;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Employee addEmployee(Employee employee) {
        employee.setEmployeeNo(setId());

        if (checkIfUserInRepo(employee)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Employee already in database");
        }

        return employeeRepository.save(employee);
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
