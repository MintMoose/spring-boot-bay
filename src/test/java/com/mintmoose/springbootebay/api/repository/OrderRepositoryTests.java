package com.mintmoose.springbootebay.api.repository;

import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.Order;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.OrderRepository;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private Long customerId;
    private Long sellerId;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        customerId = 1L;
        sellerId = 2L;
        product1 = new Product("Laptop", "A high-performance laptop", 1000.0, Categories.ELECTRONICS, false, "laptop.jpg", "seller1");
        product2 = new Product("Super Nintendo", "16-bit home video game console developed by Nintendo", 500, Categories.ELECTRONICS, false, "nintendo.png", "seller2");
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    public void orderRepository_SaveOrder_ReturnsSavedOrder() {
        // Arrange
        double totalPrice = 1000.0;
        Order newOrder = new Order(customerId, product1, totalPrice, sellerId);

        // Act
        Order savedOrder = orderRepository.save(newOrder);

        // Assert
        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getOrderId()).isGreaterThan(0);
        Assertions.assertThat(savedOrder.getOrderDate()).isNotNull();
    }

    @Test
    public void orderRepository_FindByCustomerId_ReturnsOrdersForCustomer() {
        // Arrange
        double totalPrice = 1000.0;
        Order newOrder1 = new Order(customerId, product1, totalPrice, sellerId);
        Order newOrder2 = new Order(customerId, product2, totalPrice, sellerId);

        orderRepository.save(newOrder1);
        orderRepository.save(newOrder2);

        // Act
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        // Assert
        Assertions.assertThat(orders).isNotEmpty();
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    public void orderRepository_FindBySellerId_ReturnsOrdersForSeller() {
        // Arrange
        double totalPrice = 1000.0;
        Order newOrder1 = new Order(customerId, product1, totalPrice, sellerId);
        Order newOrder2 = new Order(customerId, product2, totalPrice, sellerId);

        orderRepository.save(newOrder1);
        orderRepository.save(newOrder2);

        // Act
        List<Order> orders = orderRepository.findBySellerId(sellerId);

        // Assert
        Assertions.assertThat(orders).isNotEmpty();
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }
}
