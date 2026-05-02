package com.Aryan.Ecom_Project.Service;

import com.Aryan.Ecom_Project.Model.Product;
import com.Aryan.Ecom_Project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    // This method handles the logic of extracting file details and saving to DB
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        // Extract original name and file type from the uploaded file
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());

        // Convert the file itself into a byte array so it can be stored in the database
        product.setImageData(imageFile.getBytes());

        // Save the updated product object (with image info) to the database
        repo.save(product);
        return product;
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public Product updateproduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setId(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());
        }
        return repo.save(product);
    }

    // Search functionality: calls the repository to find products matching the keyword
    public List<Product> searchProduct(String keyword) {
        return repo.searchProduct(keyword);
    }
}
