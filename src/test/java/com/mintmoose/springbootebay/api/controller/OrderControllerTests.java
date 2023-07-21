package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Controller.OrderController;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewOrderRequest;
import com.mintmoose.springbootebay.Model.Order;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTests {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @Test
    public void testGetMyOrders() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_user");

        // Mock Customer and OrderService behavior
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(customer));

        // Create an order with a valid customerId
        Order order = new Order();
        order.setCustomerId(1L);
        List<Order> orders = Collections.singletonList(order);
        when(orderService.getOrdersByCustomerId(1L)).thenReturn(orders);

        // Perform the test
        ResponseEntity<?> response = orderController.getMyOrders(authentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    public void testGetSellingOrders() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_seller");

        // Mock Customer and OrderService behavior
        Customer seller = new Customer();
        seller.setCustomerId(2L);
        when(customerService.getCustomerByUsername("test_seller")).thenReturn(Optional.of(seller));

        // Create an order with a valid sellerId
        Order order = new Order();
        order.setSellerId(2L);
        List<Order> sellingOrders = Collections.singletonList(order);
        when(orderService.getOrdersBySellerId(2L)).thenReturn(sellingOrders);

        // Perform the test
        ResponseEntity<?> response = orderController.getSellingOrders(authentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sellingOrders, response.getBody());
    }

    @Test
    public void testCreateOrder() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_customer");

        // Mock Customer and OrderService behavior
        Customer customer = new Customer();
        customer.setCustomerId(3L);
        when(customerService.getCustomerByUsername("test_customer")).thenReturn(Optional.of(customer));

        NewOrderRequest newOrderRequest = new NewOrderRequest(1L);
        when(orderService.createOrder(newOrderRequest, customer)).thenReturn(new Order());

        // Perform the test
        ResponseEntity<?> response = orderController.createOrder(newOrderRequest, authentication);

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetOrderById() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test_customer");

        // Mock Customer and OrderService behavior
        Customer customer = new Customer();
        customer.setCustomerId(4L);
        when(customerService.getCustomerByUsername("test_customer")).thenReturn(Optional.of(customer));

        Order order = new Order();
        order.setCustomerId(4L);
        when(orderService.getOrderById(1L)).thenReturn(order);

        // Perform the test
        ResponseEntity<?> response = orderController.getOrderById(1L, authentication);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    // Add more test cases for different scenarios such as empty orders, unauthorized access, etc.
}
