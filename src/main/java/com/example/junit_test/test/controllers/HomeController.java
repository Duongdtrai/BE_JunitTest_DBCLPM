package com.example.junit_test.test.controllers;

import com.example.junit_test.test.model.Employee;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Hidden
public class HomeController {
    @GetMapping()
    public @ResponseBody String greeting() {
        return "Hello, World";
    }


    @GetMapping("/employee")
    public Employee firstPage() {
        Employee emp = new Employee();
        emp.setName("emp1");
        emp.setDesignation("manager");
        emp.setEmpId("1");
        emp.setSalary(3000);
        return emp;
    }
}
