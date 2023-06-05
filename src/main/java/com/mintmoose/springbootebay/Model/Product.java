package com.mintmoose.springbootebay.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private double price;
    @Enumerated(EnumType.STRING)
    private Categories category;
    private boolean sold;
    private String imageUrl;
    private String customerUsername; // Changed from Customer object

    public Product(String name, String description, double price, Categories category, boolean sold, String imageUrl, String customerUsername) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.sold = sold;
        this.imageUrl = imageUrl;
        this.customerUsername = customerUsername;
    }
}
