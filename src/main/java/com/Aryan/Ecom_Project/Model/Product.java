package com.Aryan.Ecom_Project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.math.BigDecimal;
import java.util.Date;

@Entity // Tells Spring Boot/JPA that this class should be a table in the database
@Data   // Generates Getters, Setters, and toString automatically using Lombok
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id // Marks this field as the Primary Key (Unique ID) for the database tableG
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate the the primary key value
    private int id;
    private String name;
    private String desc;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private Boolean available;
    private int stockQuantity;
}
