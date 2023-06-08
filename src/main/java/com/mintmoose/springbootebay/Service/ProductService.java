package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product updateProduct(Long id, NewProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (request.name() != null && !request.name().isEmpty()) {
                product.setName(request.name());
            }
            if (request.description() != null && !request.description().isEmpty()) {
                product.setDescription(request.description());
            }
            if (request.price() > 0) {
                product.setPrice(request.price());
            }
            if (request.category() != null) {
                product.setCategory(request.category());
            }
            if (request.imageUrl() != null && !request.imageUrl().isEmpty()) {
                product.setImageUrl(request.imageUrl());
            }
            product.setSold(request.sold());
            return productRepository.save(product);
        }
        throw new IllegalArgumentException("No such product exists");
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}