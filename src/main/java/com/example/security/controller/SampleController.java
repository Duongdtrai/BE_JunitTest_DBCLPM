package com.example.security.controller;

import com.example.security.dao.SampleDAO;
import com.example.security.dao.EmployeeDAO;
import org.springframework.web.bind.annotation.*;

import com.example.security.model.Sample;
import com.example.security.model.Employee;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {
    @Autowired
    private final SampleDAO sampleDAO;
    private final EmployeeDAO employeeDAO;
    
    public SampleController(SampleDAO sampleDAO, EmployeeDAO employeeDAO) {
        this.sampleDAO = sampleDAO;
        this.employeeDAO = employeeDAO;
    }
    
    @GetMapping("/samples")
    public ModelAndView getListSample(@RequestParam(name = "search", required = false) String name) {
        ModelAndView model = new ModelAndView();
        List<Sample> samples;
        if(name == null || "".equals(name)) {
            samples = sampleDAO.findAll();
        } else {
            samples = sampleDAO.findByEmployeeNameContainingIgnoreCase(name);
        }
        model.addObject("samples", samples);
         model.addObject("name", name == null ? "" : name);
        model.setViewName("sample/sample");
        return model;
    }
    
    @GetMapping("/samples/add")
    public ModelAndView addSample() { 
        ModelAndView model = new ModelAndView();
        Sample sample = new Sample();
        model.addObject("sample", sample);
        List<Employee> employees = employeeDAO.findAll();
        model.addObject("employees", employees);
        model.setViewName("sample/addSample");
        return model;
    }
    
    @GetMapping("/samples/edit/{id}")
    public ModelAndView editSample(@PathVariable Integer id) {
        ModelAndView model = new ModelAndView();
        Sample sample = sampleDAO.findSampleById(id);
        model.addObject("sample", sample);
        List<Employee> employees = employeeDAO.findAll();
        model.addObject("employees", employees);
        model.setViewName("sample/editSample");
        return model;
    }
    
    @GetMapping("/samples/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        sampleDAO.deleteById(id);
        ModelAndView model = new ModelAndView();
        List<Sample> samples = sampleDAO.findAll();
        model.addObject("samples", samples);
        return "redirect:/samples";
    }
    
    @PostMapping("/add-samples")
    public String createEmployee(@ModelAttribute Sample sample) {
        System.out.println(sample);
        sample.setCreatedAt(new Date());
        sampleDAO.save(sample);
        return "redirect:/samples";
    }

    @PostMapping("/edit-samples")
    public String updateEmployee(@ModelAttribute Sample sample) {
        System.out.println(sample);
        sample.setCreatedAt(new Date());
        sampleDAO.save(sample);
        return "redirect:/samples";
    }
}
