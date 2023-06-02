package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
