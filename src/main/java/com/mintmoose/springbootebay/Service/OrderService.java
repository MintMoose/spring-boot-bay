package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Order;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;


    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        if (isAnyProductAlreadyTaken(order)) {
            throw new IllegalArgumentException("One or more products are already taken.");
        } else {
            for (Product product : order.getProducts()) {
                product.setSold(true); // Mark the product as sold
                productService.updateProductDirectly(product); // Update the product's sold status
            }
            return orderRepository.save(order);
        }
    }

    private boolean isAnyProductAlreadyTaken(Order order) {
        List<Product> products = order.getProducts();
        for (Product product : products) {
            if (order.isProductAlreadyTaken(product)) {
                return true;
            }
        }
        return false;
    }


    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersBySellerId(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }
}
