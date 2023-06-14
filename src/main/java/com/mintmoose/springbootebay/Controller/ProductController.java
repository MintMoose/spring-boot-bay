package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.CreateProductRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page) {
        int pageSize = 10; // Number of products per page
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productsPage = productService.getAllProducts(pageable);

        if (productsPage.hasContent()) {
            List<Product> products = productsPage.getContent();
            long totalProducts = productsPage.getTotalElements();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            Map<String, Object> response = new HashMap<>();
            response.put("content", products);
            response.put("totalPages", totalPages);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProducts(@PathVariable("username") String username, @RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 10; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));

        Page<Product> productsPage = productService.getUserProducts(requestCustomer.getUsername(), pageable);
        if (productsPage.hasContent()) {
            List<Product> products = productsPage.getContent();
            long totalProducts = productsPage.getTotalElements();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            Map<String, Object> response = new HashMap<>();
            response.put("content", products);
            response.put("totalPages", totalPages);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
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
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
            try {
                Product createdProduct = productService.createProduct(request, requestCustomer.getUsername());
                return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody NewProductRequest request, Authentication authentication) {

        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
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
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
            if (Objects.equals(productService.getProductById(id).getCustomerUsername(), requestCustomer.getUsername())) {
                productService.deleteProduct(id);
                return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }
}
