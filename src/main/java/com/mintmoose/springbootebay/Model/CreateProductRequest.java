package com.mintmoose.springbootebay.Model;

public record CreateProductRequest(String name, String description, Double price, Categories category, String imageUrl) {
}
