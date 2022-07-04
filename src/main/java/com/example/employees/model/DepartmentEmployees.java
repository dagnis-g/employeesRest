package com.example.employees.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "dept_emp")
public class DepartmentEmployees {

    @Id
    @Column(name = "emp_no")
    private Integer employeeNo;

    @Column(name = "dept_no")
    private Integer departmentNo;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
    
}
