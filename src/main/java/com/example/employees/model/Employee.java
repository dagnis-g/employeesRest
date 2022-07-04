package com.example.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Integer employeeNo;

    @Column(name = "birth_date")
    @NotNull(message = "no valid birth date provided")
    @Past
    private LocalDate birthDate;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "hire_date")
    @NotNull(message = "no valid hire date provided")
    private LocalDate hireDate;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(birthDate, employee.birthDate) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && gender == employee.gender && Objects.equals(hireDate, employee.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, firstName, lastName, gender, hireDate);
    }
}
