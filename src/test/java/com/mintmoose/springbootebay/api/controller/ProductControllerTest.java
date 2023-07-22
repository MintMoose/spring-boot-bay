package com.mintmoose.springbootebay.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Controller.ProductController;
import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    private NewProductRequest newProductRequest;

    private Product productCreated;


    @BeforeEach
    void setup() {
        List<Product> products = Arrays.asList(
                new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "john_doe"),
                new Product("Product 2", "Description 2", 20.0, Categories.CLOTHING, false, "image2.jpg", "jane_smith")
        );

        newProductRequest = new NewProductRequest("Product Request", "New Product Request", 10.0, Categories.HOBBY_DIY, "image3.png");
        productCreated = new Product("Product Request", "New Product Request", 10.0, Categories.HOBBY_DIY, false, "image3.png", "john_doe");

        Long customerId = 1L; // Some unique value for the customerId
        String customerUsername = "john_doe";
        Customer authenticatedCustomer = new Customer(customerId, customerUsername, "John Doe", "john@example.com", "password");

        // Create a mock authentication object with the authenticated customer
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedCustomer, null, authorities);
        System.out.println(authentication);
        productController = new ProductController(productService, customerService);
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Create some sample products
        List<Product> products = Arrays.asList(new Product(), new Product());

        // Create a Page instance with the products
        Page<Product> productsPage = new PageImpl<>(products);

        // Mock the behavior of the productService.getAllProducts method
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products?page=0
        mockMvc.perform(get("/products").param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void testGetAllProductsNoContent() throws Exception {
        // Create an empty Page instance
        Page<Product> productsPage = new PageImpl<>(Collections.emptyList());

        // Mock the behavior of the productService.getAllProducts method
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products?page=0
        mockMvc.perform(get("/products").param("page", "0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserProducts() throws Exception {
        // Sample username
        String username = "john_doe";

        // Create a sample customer
        Customer customer = new Customer();
        customer.setUsername(username);

        // Create some sample products
        List<Product> products = Arrays.asList(new Product(), new Product());

        // Create a Page instance with the products
        Page<Product> productsPage = new PageImpl<>(products);

        // Mock the behavior of the customerService.getCustomerByUsername method to return the customer
        when(customerService.getCustomerByUsername(username)).thenReturn(Optional.of(customer));

        // Mock the behavior of the productService.getUserProducts method
        when(productService.getUserProducts(eq(username), any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products/{username}?page=0
        mockMvc.perform(get("/products/{username}", username).param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2))) // Assuming there are 2 sample products
                .andExpect(jsonPath("$.totalPages").value(1)); // Assuming there is only one page of products
    }

    @Test
    void testGetUserProductsNoContent() throws Exception {
        // Sample username
        String username = "john_doe";

        // Create an empty Page instance
        Page<Product> productsPage = new PageImpl<>(Collections.emptyList());

        // Create a sample customer
        Customer customer = new Customer();
        customer.setUsername(username);

        // Mock the behavior of the customerService.getCustomerByUsername method
        when(customerService.getCustomerByUsername(username)).thenReturn(Optional.of(customer));

        // Mock the behavior of the productService.getUserProducts method
        when(productService.getUserProducts(eq(username), any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products/{username}?page=0
        mockMvc.perform(get("/products/{username}", username).param("page", "0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUnSoldUserProducts() throws Exception {
        // Sample username
        String username = "john_doe";

        // Create a sample customer
        Customer customer = new Customer();
        customer.setUsername(username);

        // Create some sample products
        List<Product> products = Arrays.asList(new Product(), new Product());

        // Create a Page instance with the products
        Page<Product> productsPage = new PageImpl<>(products);

        // Mock the behavior of the customerService.getCustomerByUsername method to return the customer
        when(customerService.getCustomerByUsername(username)).thenReturn(Optional.of(customer));

        // Mock the behavior of the productService.getUnsoldUserProducts method
        when(productService.getUnsoldUserProducts(eq(username), any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products/{username}/unsold?page=0
        mockMvc.perform(get("/products/{username}/unsold", username).param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2))) // Assuming there are 2 sample products
                .andExpect(jsonPath("$.totalPages").value(1)); // Assuming there is only one page of products
    }

    @Test
    void testGetUnSoldUserProductsNoContent() throws Exception {
        // Sample username
        String username = "john_doe";

        // Create an empty Page instance
        Page<Product> productsPage = new PageImpl<>(Collections.emptyList());

        // Create a sample customer
        Customer customer = new Customer();
        customer.setUsername(username);

        // Mock the behavior of the customerService.getCustomerByUsername method
        when(customerService.getCustomerByUsername(username)).thenReturn(Optional.of(customer));

        // Mock the behavior of the productService.getUnsoldUserProducts method
        when(productService.getUnsoldUserProducts(eq(username), any(Pageable.class))).thenReturn(productsPage);

        // Perform the GET request to /products/{username}/unsold?page=0
        mockMvc.perform(get("/products/{username}/unsold", username).param("page", "0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductById() throws Exception {
        // Sample product ID
        Long productId = 1L;

        // Create a sample product
        Product product = new Product("Product 1", "Description 1", 10.0, Categories.ELECTRONICS, false, "image1.jpg", "john_doe");
        product.setId(productId);

        // Mock the behavior of the productService.getProductById method
        when(productService.getProductById(productId)).thenReturn(product);

        // Perform the GET request to /products/details/{id}
        mockMvc.perform(get("/products/details/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

    @Test
    void testGetProductByIdNotFound() throws Exception {
        // Sample non-existent product ID
        Long productId = 999L;

        // Mock the behavior of the productService.getProductById method
        when(productService.getProductById(productId)).thenReturn(null);

        // Perform the GET request to /products/details/{id}
        mockMvc.perform(get("/products/details/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock Customer and ProductService behavior
        Customer customer = new Customer();
        customer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(customer));

        // Set properties for the new product request

        Product createdProduct = new Product();
        // Set properties for the created product
        when(productService.createProduct(newProductRequest, "test_user")).thenReturn(createdProduct);

        // Perform the test
        ResponseEntity<?> response = productController.createProduct(newProductRequest, authentication);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdProduct, response.getBody());
    }



    @Test
    void testCreateProductUnauthorized() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Perform the test
        ResponseEntity<?> response = productController.createProduct(newProductRequest, authentication);

        // Assert the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // Add more assertions as needed
    }

    @Test
    void testUpdateProduct() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("john_doe");

        // Mock Customer and ProductService behavior
        Customer customer = new Customer();
        customer.setUsername("john_doe");
        when(customerService.getCustomerByUsername("john_doe")).thenReturn(Optional.of(customer));

        Long productId = 1L;
        // Set properties for the new product request

        // Set properties for the updated product
        when(productService.getProductById(productId)).thenReturn(productCreated);
        when(productService.updateProduct(productId, newProductRequest)).thenReturn(productCreated);

        // Perform the test
        ResponseEntity<?> response = productController.updateProduct(productId, newProductRequest, authentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productCreated, response.getBody());
    }


    @Test
    void testUpdateProductUnauthorized() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Perform the test
        ResponseEntity<?> response = productController.updateProduct(1L, newProductRequest, authentication);

        // Assert the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // Add more assertions as needed
    }

    @Test
    void testDeleteProduct() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock ProductService behavior
        Long productId = 1L;
        Product product = new Product();
        product.setCustomerUsername("john_doe");
        when(productService.getProductById(productId)).thenReturn(product);

        // Perform the test
        ResponseEntity<?> response = productController.deleteProduct(productId, authentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Add more assertions as needed
    }

    @Test
    void testDeleteProductUnauthorized() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Perform the test
        ResponseEntity<?> response = productController.deleteProduct(1L, authentication);

        // Assert the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // Add more assertions as needed
    }


}
