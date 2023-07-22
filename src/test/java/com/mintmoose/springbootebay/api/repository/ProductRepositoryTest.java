package com.mintmoose.springbootebay.api.repository;

import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        Product product1 = new Product("Laptop", "A high-performance laptop", 1000.0, Categories.ELECTRONICS, false, "laptop.jpg", "seller1");
        Product product2 = new Product("Smartphone", "A powerful smartphone", 500.0, Categories.ELECTRONICS, false, "smartphone.jpg", "seller2");
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    public void productRepository_FindAllByCustomerUsername_ReturnsProductsForCustomer() {
        // Arrange
        String customerUsername = "seller1";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Product> products = productRepository.findAllByCustomerUsername(customerUsername, pageable);

        // Assert
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void productRepository_FindUnsoldByCustomerId_ReturnsUnsoldProductsForCustomer() {
        // Arrange
        String customerUsername = "seller1";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Product> products = productRepository.findUnSoldByCustomerId(customerUsername, pageable);

        // Assert
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void productRepository_FindUnsoldProducts_ReturnsUnsoldProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Product> products = productRepository.findUnsoldProducts(pageable);

        // Assert
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void productRepository_FindAllProducts_ReturnsAllProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Product> products = productRepository.findAllProducts(pageable);

        // Assert
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void productRepository_FindAllByCustomerUsernameUnSold_ReturnsUnsoldProductsForCustomer() {
        // Arrange
        String customerUsername = "seller1";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Product> products = productRepository.findAllByCustomerUsernameUnSold(customerUsername, pageable);

        // Assert
        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getTotalElements()).isEqualTo(1);
    }
}
