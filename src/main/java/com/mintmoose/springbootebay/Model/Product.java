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
    private Double price;
    @Enumerated(EnumType.STRING)
    private Categories category;
    private Boolean sold;
    private String imageUrl;
    private Long customerId;



    public Product(String name, String description, double price, Categories category, boolean sold, String imageUrl, Long customerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.sold = sold;
        this.imageUrl = imageUrl;
        this.customerId = customerId;
    }
}
