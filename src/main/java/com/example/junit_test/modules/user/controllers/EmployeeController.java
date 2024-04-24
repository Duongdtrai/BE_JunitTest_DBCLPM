package com.example.junit_test.modules.user.controllers;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.user.entities.Employee;
import com.example.junit_test.modules.user.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
@Tag(name = "Employees", description = "Employees APIs")
public class EmployeeController {
  @Autowired
  private final EmployeeService service;

  @GetMapping("/list")
  public ResponseEntity<SystemResponse<List<Employee>>> list() {
    return service.list();
  }

  @PatchMapping("/update")
  public ResponseEntity<SystemResponse<Employee>> update(@RequestBody Employee data) {
    return service.update(data);
  }
}
