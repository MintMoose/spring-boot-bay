package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
//    private final PasswordEncoder passwordEncoder;


    @Autowired
    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<Product> getAllCustomers(Pageable pageable) {
        return customerRepository.findAllCustomers(pageable);
    }

    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public void createCustomer(NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setUsername(request.username());
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public Customer updateCustomer(Long customerId, NewCustomerRequest request) {
        Customer oldCustomer = getCustomer(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer exists"));


        if (!request.username().isEmpty()) {
            oldCustomer.setUsername(request.username());
        }
        if (!request.name().isEmpty()) {
            oldCustomer.setName(request.name());
        }
        if (!request.email().isEmpty()) {
            oldCustomer.setEmail(request.email());
        }
        if (!request.password().isEmpty()) {
            oldCustomer.setPassword(request.password());
        }
        return customerRepository.save(oldCustomer);

    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
