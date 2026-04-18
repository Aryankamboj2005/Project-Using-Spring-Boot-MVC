package com.Aryan.Ecom_Project.Repository;

import com.Aryan.Ecom_Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    // for searching
    // Custom Query to search for products by name or brand
    // LOWER() makes it case-insensitive
    // CONCAT('%', :keyword, '%') allows searching for partial matches anywhere in the string
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProduct(String keyword);
}
