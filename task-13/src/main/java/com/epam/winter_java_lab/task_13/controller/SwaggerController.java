package com.epam.winter_java_lab.task_13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SwaggerController {
    @GetMapping
    public String swaggerUi() {

        return "redirect:/swagger-ui.html";
    }

}
