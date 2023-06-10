package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.CreateProductRequest;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    int pageNumber = 0; // Page number (0-indexed)
    int pageSize = 10; // Number of products per page

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public List<Product> getUserProducts(String customerUsername) {
        Page<Product> productsPage = productRepository.findAllByCustomerUsername(customerUsername, pageable);
        return productsPage.getContent();
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

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllCustomers(pageable);
    }

    public Product createProduct(CreateProductRequest params, String username) {

        if (params.name() == null ||
                params.description() == null ||
                params.price() == null ||
                params.category() == null ||
                params.imageUrl() == null) {
            throw new IllegalArgumentException("Missing required parameters for creating a product.");
        }

        Product product = new Product();
        product.setName(params.name());
        product.setDescription(params.description());
        product.setPrice(params.price());
        product.setCategory(params.category());
        product.setImageUrl(params.imageUrl());
        product.setCustomerUsername(username);

        return productRepository.save(product);
    }



}
