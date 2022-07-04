package com.example.employees.repo;

import com.example.employees.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query(value = "select d.departmentName from Department d group by d.departmentName")
    List<String> getDepartmentNames();
    
}
