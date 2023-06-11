package com.mintmoose.springbootebay.Controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminProductController {

    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest product, @RequestBody String username) {
        Product createdProduct = productService.createProduct(product, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody NewProductRequest product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 300; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = productService.getAllProducts(pageable);
        if (productsPage.hasContent()) {
            return ResponseEntity.ok(productsPage.getContent());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProducts(@PathVariable("username") String username, @RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 300; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Customer requestCustomer = customerService.getCustomerByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        return ResponseEntity.ok(productService.getUserProducts(requestCustomer.getUsername(), pageable));
    }
}
