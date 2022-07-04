package com.example.employees.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "titles")
public class Title {

    @Id
    @Column(name = "emp_no")
    private Integer employeeNo;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "from_date")
    @NotNull
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
