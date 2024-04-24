package com.example.junit_test.modules.user.repository;

import com.example.junit_test.modules.user.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
