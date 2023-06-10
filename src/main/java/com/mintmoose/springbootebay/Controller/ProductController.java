package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.CreateProductRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProducts(@PathVariable("username") String username) {
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        return ResponseEntity.ok(productService.getUserProducts(requestCustomer.getUsername()));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Product requestProduct = productService.getProductById(id);
        if (requestProduct != null) {
            return ResponseEntity.ok(requestProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist.");
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
        if (jwtService.isTokenValid(token, requestCustomer)) {
            try {
                Product createdProduct = productService.createProduct(request, username);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody NewProductRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
        if (jwtService.isTokenValid(token, requestCustomer)) {
            if (Objects.equals(productService.getProductById(id).getCustomerUsername(), requestCustomer.getUsername())) {
                Product updatedProduct = productService.updateProduct(id, request);
                if (updatedProduct != null) {
                    return ResponseEntity.ok(updatedProduct);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
        if (jwtService.isTokenValid(token, requestCustomer)) {
            if (Objects.equals(productService.getProductById(id).getCustomerUsername(), requestCustomer.getUsername())) {
                productService.deleteProduct(id);
                return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }
}
