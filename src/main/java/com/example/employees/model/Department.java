package com.example.employees.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "dept_no")
    private String departmentNo;

    @Column(name = "dept_name")
    private String departmentName;
    
}
