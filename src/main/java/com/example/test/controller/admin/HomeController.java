package com.example.test.controller.admin;

import com.example.test.annotation.AdminController;
import org.springframework.security.core.parameters.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AdminController
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        return "/admin/home";
    }// home

}// HomeController
