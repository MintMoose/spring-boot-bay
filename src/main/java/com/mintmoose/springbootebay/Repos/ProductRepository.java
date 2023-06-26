package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.customerId = ?1")
    Page<Product> findAllByCustomerId(Long customerId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.customerId = ?1 AND p.sold = false")
    Page<Product> findUnSoldByCustomerId(Long customerId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.sold = false")
    Page<Product> findUnsoldProducts(Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);
}
