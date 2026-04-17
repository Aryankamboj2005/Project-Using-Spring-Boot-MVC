package com.Aryan.Ecom_Project.Repository;

import com.Aryan.Ecom_Project.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
