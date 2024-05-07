package com.example.security.controller;

import com.example.security.dao.EmployeeDAO;
import org.springframework.web.bind.annotation.*;

import com.example.security.model.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {
    @Autowired
    private final EmployeeDAO employeeDAO;
    
    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    
    @GetMapping("/employees")
    public ModelAndView getListEmployee(@RequestParam(name = "search", required = false) String name) {
        ModelAndView model = new ModelAndView();
        System.out.println("name: " + name);
        List<Employee> employees;
        if(name != null || "".equals(name)) {
            employees = employeeDAO.findByNameContainingIgnoreCase(name);
        } else {
            employees = employeeDAO.findAll();
        }
        model.addObject("employees", employees);
        model.addObject("name", name == null ? "" : name);
        model.setViewName("employee/employee");
        return model;
    }
    
    @GetMapping("/employees/add")
    public ModelAndView addEmployee() { 
        ModelAndView model = new ModelAndView();
        Employee employee = new Employee();
        model.addObject("employee", employee);
        model.setViewName("employee/addEmployee");
        return model;
    }
    
    @GetMapping("/employees/edit/{id}")
    public ModelAndView editEmployee(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Employee employee = employeeDAO.findEmployeeById(id);
        model.addObject("employee", employee);
        model.setViewName("employee/editEmployee");
        return model;
    }
    
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        employeeDAO.deleteById(id);
        ModelAndView model = new ModelAndView();
        List<Employee> employees = employeeDAO.findAll();
        model.addObject("employees", employees);
        return "redirect:/employees";
    }
    
    @PostMapping("/add-employees")
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employeeDAO.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/edit-employees")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        System.out.println(employee);
        employeeDAO.save(employee);
        return "redirect:/employees";
    }
}
