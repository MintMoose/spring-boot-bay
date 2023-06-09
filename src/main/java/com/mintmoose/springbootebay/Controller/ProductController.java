package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.CreateProductRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.CustomerService;
import com.mintmoose.springbootebay.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Product createdProduct = productService.createProduct(request, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    @GetMapping
    public ResponseEntity<?> getAllProducts(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
        return ResponseEntity.ok(productService.getUserProducts(requestCustomer.getCustomerId()));
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody NewProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }
}
