package com.mintmoose.springbootebay.Controller;
import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Order;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    private Customer getRequestCustomer(Authentication authentication) {
        String username = authentication.getName();
        return customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
    }


    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(Authentication authentication) {
        Customer requestCustomer = getRequestCustomer(authentication);
        List<Order> myOrders = orderService.getOrdersByCustomerId(requestCustomer.getCustomerId());

        // Check if the user is authorized to access the orders
        if (isCustomerOrderAccessible(myOrders, requestCustomer)) {
            if (!myOrders.isEmpty()) {
                return ResponseEntity.ok(myOrders);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the customer.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
        }
    }


    @GetMapping("/selling-orders")
    public ResponseEntity<?> getSellingOrders(Authentication authentication) {
        Customer requestSeller= getRequestCustomer(authentication);
        List<Order> sellingOrders = orderService.getOrdersBySellerId(requestSeller.getCustomerId());

        // Check if the user is authorized to access the orders
        if (isSellerOrderAccessible(sellingOrders, requestSeller)) {
            return ResponseEntity.ok(sellingOrders);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") Long orderId, Authentication authentication) {
        Customer requestCustomer = getRequestCustomer(authentication);
        Order order = orderService.getOrderById(orderId);

        // Check if the order exists
        if (order != null) {
            // Check if the user is authorized to access the order
            if (isOrderAccessible(order, requestCustomer)) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }

    private boolean isOrderAccessible(Order order, Customer requestCustomer) {
        // Check if the customer ID or seller ID is a match with the requesting customer
        return order.getCustomerId().equals(requestCustomer.getCustomerId())
                || order.getSellerId().equals(requestCustomer.getCustomerId());
    }

    private boolean isCustomerOrderAccessible(List<Order> orders, Customer requestCustomer) {
        for (Order order : orders) {
            if (order.getCustomerId().equals(requestCustomer.getCustomerId())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSellerOrderAccessible(List<Order> orders, Customer requestSeller) {
        for (Order order : orders) {
            if (order.getSellerId().equals(requestSeller.getCustomerId())) {
                return true;
            }
        }
        return false;
    }

}
