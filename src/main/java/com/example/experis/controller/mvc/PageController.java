package com.example.experis.controller.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Value("${app_name}")
    private String appName;

    @GetMapping("/")
    public String index(Model model) {
        // Add attributes to the model to be read by Thymeleaf
        model.addAttribute("app_name", appName);
        model.addAttribute("route", "home");

        // This will return the 'index' view
        return "home";
    }

}
