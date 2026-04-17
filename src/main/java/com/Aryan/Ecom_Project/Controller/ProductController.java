package com.Aryan.Ecom_Project.Controller;

import com.Aryan.Ecom_Project.Model.Product;
import com.Aryan.Ecom_Project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin 
@RestController 
@RequestMapping("/api") 
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/") 
    public String greet() {
        return "Hello World";
    }

    @GetMapping("/products") 
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id) {
        return service.getProductById(id);
    }
}
