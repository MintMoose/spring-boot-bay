package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewProductRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    public Page<Product> getUserProducts(String customerUsername, Pageable pageable) {

        return productRepository.findAllByCustomerUsername(customerUsername, pageable);
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
            return productRepository.save(product);
        }
        throw new IllegalArgumentException("No such product exists");
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }

    public Page<Product> getUnsoldProducts(Pageable pageable) {
        return productRepository.findUnsoldProducts(pageable);
    }

    public Product createProduct(NewProductRequest params, String customerUsername) {

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
        product.setSold(false);
        product.setCategory(params.category());
        product.setImageUrl(params.imageUrl());
        product.setCustomerUsername(customerUsername);

        return productRepository.save(product);
    }


    public void updateProductDirectly(Product product) {
        productRepository.save(product);
    }

    public Page<Product> getUnsoldUserProducts(String username, Pageable pageable) {
        return productRepository.findAllByCustomerUsernameUnSold(username, pageable);
    }
}
