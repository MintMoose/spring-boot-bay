package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

    @Query("SELECT c FROM Customer c")
    Page<Customer> findAllCustomers(Pageable pageable);

    Optional<Customer> findByEmail(String email);
}
