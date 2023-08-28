package com.example.test.controller.admin;

import com.example.test.annotation.AdminController;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AdminController
@Log4j2
public class LoginController {

    @GetMapping("/login")
    public String adminLoginPage(@RequestParam(name = "error", required = false) String error, @RequestParam(name = "errmsg", required = false) String errmsg, Model model) {
        model.addAttribute("error" , error );
        model.addAttribute("errmsg", errmsg);

        return "/admin/login";
    }// adminLoginPage

}// AdminLoginController











