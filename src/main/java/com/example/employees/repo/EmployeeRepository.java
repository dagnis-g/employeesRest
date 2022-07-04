package com.example.employees.repo;

import com.example.employees.model.Employee;
import com.example.employees.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findTopByOrderByEmployeeNoDesc();

    boolean existsByBirthDateAndFirstNameAndLastNameAndGender(LocalDate birthDate,
                                                              String firstName,
                                                              String lastName,
                                                              Gender gender);

    Page<Employee> findEmployeeByGender(Gender gender, Pageable pageable);

    Page<Employee> findEmployeeByHireDateBefore(LocalDate date, Pageable pageable);

    Page<Employee> findEmployeeByHireDateAfter(LocalDate date, Pageable pageable);

    @Query(value = "SELECT e FROM Employee e " +
            "JOIN Title t ON t.employeeNo = e.employeeNo " +
            "WHERE ((t.title LIKE :title) " +
            "AND (:gender is null or e.gender LIKE :gender)" +
            "AND(:hireDateBefore is null or e.hireDate < :hireDateBefore))")
    Page<Employee> findEmployeesByTitleSortFilterBeforeHireDate(
            String title,
            Gender gender,
            LocalDate hireDateBefore,
            Pageable pageable);

    @Query(value = "SELECT e FROM Employee e " +
            "JOIN Title t ON t.employeeNo = e.employeeNo " +
            "WHERE ((t.title LIKE :title) " +
            "AND (:gender is null or e.gender LIKE :gender)" +
            "AND(:hireDateAfter is null or e.hireDate > :hireDateAfter))")
    Page<Employee> findEmployeesByTitleSortFilterAfterAfterHireDate(
            String title,
            Gender gender,
            LocalDate hireDateAfter,
            Pageable pageable);

    @Query(value = "SELECT e FROM Employee e " +
            "JOIN Title t ON t.employeeNo = e.employeeNo " +
            "WHERE (t.title LIKE :title) " +
            "AND (:gender is null or e.gender LIKE :gender)")
    Page<Employee> findEmployeesByTitleSortFilter(
            String title,
            Gender gender,
            Pageable pageable);

}
