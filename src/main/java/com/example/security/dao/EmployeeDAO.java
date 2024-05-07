package com.example.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.security.model.Employee;
import java.util.List;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
    List<Employee> findByNameContainingIgnoreCase(String name);
    Employee findEmployeeById(Integer id);
}