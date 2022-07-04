package com.example.employees.repo;

import com.example.employees.model.DepartmentEmployees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployees, Integer> {
}
