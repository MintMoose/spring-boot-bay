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

import java.util.List;

@RestController
@RequestMapping("/open")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AnonymousController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber) {
        int pageSize = 20; // Number of products per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = productService.getAllProducts(pageable);
        if (productsPage.hasContent()) {
            return ResponseEntity.ok(productsPage.getContent());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found.");
    }
}
