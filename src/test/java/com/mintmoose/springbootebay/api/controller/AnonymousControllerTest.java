package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Controller.AnonymousController;
import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnonymousControllerTest {

    @InjectMocks
    private AnonymousController anonymousController;

    @Mock
    private ProductService productService;

    @Test
    public void testGetAllProducts_Success() {
        // Mock ProductService behavior
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "john_doe"));

        products.add( new Product("Product 2", "Description 2", 20.0, Categories.CLOTHING, false, "image2.jpg", "jane_smith"));

        Page<Product> productsPage = new PageImpl<>(products);
        when(productService.getAllProducts(pageable)).thenReturn(productsPage);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getAllProducts(pageNumber);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("content"));
        assertTrue(responseBody.containsKey("totalPages"));
        List<Product> responseProducts = (List<Product>) responseBody.get("content");
        assertEquals(products.size(), responseProducts.size());
        assertEquals(products.get(0).getId(), responseProducts.get(0).getId());
        assertEquals(products.get(1).getId(), responseProducts.get(1).getId());
        // Add more assertions based on your specific use case
    }

    @Test
    public void testGetAllProducts_NoProductsFound() {
        // Mock ProductService behavior
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = new PageImpl<>(Collections.emptyList());
        when(productService.getAllProducts(pageable)).thenReturn(productsPage);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getAllProducts(pageNumber);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }

    @Test
    public void testGetProductById_Success() {
        // Mock ProductService behavior
        Long productId = 1L;
        Product product = new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "john_doe");

        when(productService.getProductById(productId)).thenReturn(product);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getProductById(productId);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Product responseProduct = (Product) response.getBody();
        assertNotNull(responseProduct);
        assertEquals("Product 1", responseProduct.getName());
        assertEquals("Description 1", responseProduct.getDescription());
        // Add more assertions based on your specific use case
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        // Mock ProductService behavior
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(null);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getProductById(productId);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }

    @Test
    public void testGetUnsoldProducts_Success() {
        // Mock ProductService behavior
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "john_doe"));

        products.add(new Product("Product 2", "Description 2", 20.0, Categories.CLOTHING, false, "image2.jpg", "jane_smith"));

        Page<Product> productsPage = new PageImpl<>(products);
        when(productService.getUnsoldProducts(pageable)).thenReturn(productsPage);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getUnsoldProducts(pageNumber);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.containsKey("content"));
        assertTrue(responseBody.containsKey("totalPages"));
        List<Product> responseProducts = (List<Product>) responseBody.get("content");
        assertEquals(products.size(), responseProducts.size());
        assertEquals(products.get(0).getId(), responseProducts.get(0).getId());
        assertEquals(products.get(1).getId(), responseProducts.get(1).getId());
        // Add more assertions based on your specific use case
    }

    @Test
    public void testGetUnsoldProducts_NoProductsFound() {
        // Mock ProductService behavior
        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = new PageImpl<>(Collections.emptyList());
        when(productService.getUnsoldProducts(pageable)).thenReturn(productsPage);

        // Perform the test
        ResponseEntity<?> response = anonymousController.getUnsoldProducts(pageNumber);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }

    // Add more test cases for different scenarios of getAllProducts, getProductById, and getUnsoldProducts endpoints.
}
