package com.example.test.controller.admin;

import com.example.test.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Log4j2
public class ResourceController {

    private ResourceService resourceService;

//    @GetMapping("/resources")
//    public String

}// ResourceController


















