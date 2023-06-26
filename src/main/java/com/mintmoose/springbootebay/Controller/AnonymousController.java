package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/open")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AnonymousController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 10; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
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

    @GetMapping("/products/sale")
    public ResponseEntity<?> getUnsoldProducts(@RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 10; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = productService.getUnsoldProducts(pageable);

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
}
