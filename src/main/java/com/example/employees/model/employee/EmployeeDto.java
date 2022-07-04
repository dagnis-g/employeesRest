package com.example.employees.model.employee;

import com.example.employees.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @NotNull(message = "no valid birth date provided")
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "no valid hire date provided")
    private LocalDate hireDate;

}
