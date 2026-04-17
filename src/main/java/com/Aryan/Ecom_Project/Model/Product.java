package com.Aryan.Ecom_Project.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Entity // Tells Spring Boot/JPA that this class should be a table in the database
@Data   // Generates Getters, Setters, and toString automatically using Lombok
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id // Marks this field as the Primary Key (Unique ID) for the database tableG
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate the the primary key value
    private Integer id;
    private String name;
    @Column(name="`desc`")
    private String desc;
    private String brand;
    private BigDecimal price;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private Boolean available;
    private Integer stockQuantity;

    private String imageName;
    private String imageType;
    @Lob // if the image is too large this help to save the large object into the database
    private byte[] imageData; // to storing the image or giving memeory loction for the image

}
