package com.example.employees.repo;

import com.example.employees.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title, Integer> {

    @Query(value = "select t.title from Title t group by t.title")
    List<String> findAllTitles();

}
