package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.customerUsername = ?1")
    List<Product> findAllByCustomerUsername(String customerUsername);

}
