package com.hanium.showerendorphins.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:8080/swagger-ui/
@RestController
@RequestMapping("/api")
public class SwaggerController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
