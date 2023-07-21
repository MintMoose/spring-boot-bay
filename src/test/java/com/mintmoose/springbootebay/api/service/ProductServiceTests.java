package com.mintmoose.springbootebay.api.service;

import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import com.mintmoose.springbootebay.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ProductExists_ReturnsProduct() {
        // Arrange
        Long productId = 1L;
        Product expectedProduct = new Product("Test Product", "Description", 10.0, Categories.ELECTRONICS, false, "image.jpg", "user");
        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    void getProductById_ProductDoesNotExist_ReturnsNull() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Product actualProduct = productService.getProductById(productId);

        // Assert
        assertNull(actualProduct);
    }

    @Test
    void updateProduct_ProductExists_ProductUpdated() {
        // Arrange
        Long productId = 1L;
        NewProductRequest request = new NewProductRequest("Updated Product", "Updated Description", 20.0, Categories.CLOTHING, "updated.jpg");
        Product existingProduct = new Product("Test Product", "Description", 10.0, Categories.ELECTRONICS, false, "image.jpg", "user");
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product updatedProduct = productService.updateProduct(productId, request);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(request.name(), updatedProduct.getName());
        assertEquals(request.description(), updatedProduct.getDescription());
        assertEquals(request.price(), updatedProduct.getPrice());
        assertEquals(request.category(), updatedProduct.getCategory());
        assertEquals(request.imageUrl(), updatedProduct.getImageUrl());
        assertFalse(updatedProduct.getSold());
    }

    @Test
    void updateProduct_ProductDoesNotExist_ThrowsException() {
        // Arrange
        Long productId = 1L;
        NewProductRequest request = new NewProductRequest("Updated Product", "Updated Description", 20.0, Categories.CLOTHING, "updated.jpg");
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, request));
    }

    @Test
    void createProduct_ValidParameters_ProductCreated() {
        // Arrange
        NewProductRequest request = new NewProductRequest("New Product", "Product Description", 30.0, Categories.ELECTRONICS, "new_image.jpg");
        String customerUsername = "user";
        Product expectedProduct = new Product("New Product", "Product Description", 30.0, Categories.ELECTRONICS, false, "new_image.jpg", "user");
        when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);

        // Act
        Product createdProduct = productService.createProduct(request, customerUsername);

        // Assert
        assertNotNull(createdProduct);
        assertEquals(expectedProduct, createdProduct);
    }

    @Test
    void createProduct_MissingParameters_ThrowsException() {
        // Arrange
        NewProductRequest request = new NewProductRequest("New Product", null, 30.0, Categories.ELECTRONICS, "new_image.jpg");
        String customerUsername = "user";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request, customerUsername));
    }

    @Test
    void deleteProduct_ValidId_ProductDeleted() {
        // Arrange
        Long productId = 1L;

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void getAllProducts_ValidPageable_ReturnsPageOfProducts() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        List<Product> productList = List.of(
                new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "user1"),
                new Product("Product 2", "Description 2", 20.0, Categories.CLOTHING, false, "image2.jpg", "user2")
        );
        Page<Product> expectedPage = new PageImpl<>(productList, pageable, productList.size());
        when(productRepository.findAllProducts(pageable)).thenReturn(expectedPage);

        // Act
        Page<Product> actualPage = productService.getAllProducts(pageable);

        // Assert
        assertNotNull(actualPage);
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getUnsoldProducts_ValidPageable_ReturnsPageOfUnsoldProducts() {
        // Arrange
        Pageable pageable = Pageable.unpaged();
        List<Product> unsoldProductList = List.of(
                new Product("Unsold Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "user1"),
                new Product("Unsold Product 2", "Description 2", 20.0, Categories.CLOTHING, false, "image2.jpg", "user2")
        );
        Page<Product> expectedPage = new PageImpl<>(unsoldProductList, pageable, unsoldProductList.size());
        when(productRepository.findUnsoldProducts(pageable)).thenReturn(expectedPage);

        // Act
        Page<Product> actualPage = productService.getUnsoldProducts(pageable);

        // Assert
        assertNotNull(actualPage);
        assertEquals(expectedPage, actualPage);
    }
}
