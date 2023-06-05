package com.mintmoose.springbootebay.Model;

public record NewProductRequest(String name, String description, double price, Categories category, boolean sold, String imageUrl) {
}
