package com.Aryan.Ecom_Project.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Tells Spring this class handles HTTP requests (like GET, POST)
@RequestMapping("/api") // Sets the base URL path for all methods in this class
public class ProductController {
    @RequestMapping("/") // Maps this method to http://localhost:8080/api/
    public String greet() {
        return "Hello World";
    }
}
