package com.example.junit_test.modules.user.service;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.user.entities.Employee;
import com.example.junit_test.modules.user.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
  private final EmployeeRepository repository;

  public ResponseEntity<SystemResponse<List<Employee>>> list() {
    try {
      List<Employee> employeeList = repository.findAll();
      return Response.ok(employeeList);
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }

  public ResponseEntity<SystemResponse<Employee>> update(Employee data) {
    try {
      return Response.ok(repository.save(data));
    } catch (Exception e) {
      return Response.badRequest(500, e.getMessage());
    }
  }
}
