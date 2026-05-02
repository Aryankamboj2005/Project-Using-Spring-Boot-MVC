package com.Aryan.Ecom_Project.Controller;

import com.Aryan.Ecom_Project.Model.Product;
import com.Aryan.Ecom_Project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Method to add a new product along with an image file
    // @RequestPart is used because we are receiving two different parts: a JSON
    // 'product' and a file 'imageFile'
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
            @RequestPart MultipartFile imageFile) {
        System.out.println("Received Product: " + product);
        try {
            // Hand the product and image to the Service layer for logic and saving
            Product p1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(p1, HttpStatus.CREATED); // Return the saved product with 201 Created status
        } catch (Exception e) {
            // If anything goes wrong, send the error message back to the frontend
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Method to serve the image for a specific product
    // It returns the raw byte array (the image) and tells the browser what type of
    // image it is (jpeg, png, etc.)
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product == null || product.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageData = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType())) // Tell the browser "This is an image"
                .body(imageData); // Send the actual image data
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,
            @RequestPart Product product,
            @RequestPart(required = false) MultipartFile imageFile) throws IOException {
        Product p1 = service.updateproduct(id, product, imageFile);
        if (p1 != null) {
            return new ResponseEntity<>("updated", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product p1 = service.getProductById(id);
        if(p1!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("deleted", HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }


    }
    // This endpoint handles the search functionality based on a keyword
    // @RequestParam extracts the value from the URL query (e.g., ?keyword=iphone)
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> getProductBySearch(@RequestParam String keyword) {
        // Pass the extracted keyword to the service layer
        List<Product> products = service.searchProduct(keyword);
        // Return the list of matching products with HTTP 200 OK
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserProfile(@org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.oauth2.core.user.OAuth2User principal) {
        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(principal.getAttributes(), HttpStatus.OK);
    }

}
