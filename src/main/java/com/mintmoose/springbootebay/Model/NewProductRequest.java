package com.mintmoose.springbootebay.Model;

public record NewProductRequest(String name, String description, Double price, Categories category, String imageUrl) {
}
