package com.example.security.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView editSample() {
        ModelAndView model = new ModelAndView();
        model.setViewName("/home");
        return model;
    }
}
