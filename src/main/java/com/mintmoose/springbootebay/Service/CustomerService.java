package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
