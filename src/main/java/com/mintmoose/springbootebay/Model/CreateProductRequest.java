package com.mintmoose.springbootebay.Model;

public record CreateProductRequest(String name, String description, double price, Categories category, String imageUrl) {
}
