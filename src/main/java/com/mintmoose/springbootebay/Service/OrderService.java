package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewOrderRequest;
import com.mintmoose.springbootebay.Model.Order;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;


    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(NewOrderRequest order, Customer customer) {
        Product product = productService.getProductById(order.productId());
        if (product == null) {
           throw new IllegalArgumentException("Product Doesnt exist!");
        }
        Customer seller = customerService.getCustomerByUsername(product.getCustomerUsername())
                .orElseThrow(() -> new IllegalArgumentException("Product is incorrect? (How did you get here)"));
        Order newOrder = new Order(customer.getCustomerId(), product, product.getPrice(), seller.getCustomerId());
        if (product.getSold()) {
            throw new IllegalArgumentException("products are already taken.");
        } else {
                product.setSold(true); // Mark the product as sold
                productService.updateProductDirectly(product); // Update the product's sold status

                return orderRepository.save(newOrder);
        }
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
