package com.example.junit_test.controllers;

import com.example.junit_test.model.Employee;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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
