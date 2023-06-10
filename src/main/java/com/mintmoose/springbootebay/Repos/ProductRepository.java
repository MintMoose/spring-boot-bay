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

    @Query("SELECT p FROM Product p WHERE p.customerUsername = ?1")
    Page<Product> findAllByCustomerUsername(String customerUsername, Pageable pageable);

    @Query("SELECT p FROM Product p")
    Page<Product> findAllCustomers(Pageable pageable);

}
