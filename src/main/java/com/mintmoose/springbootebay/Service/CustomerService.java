package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public void createCustomer(NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    public void updateCustomer(Long customerId, NewCustomerRequest request) {
        Customer oldCustomer = getCustomer(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer exists"));


        if (!request.firstName().isEmpty()) {
            oldCustomer.setFirstName(request.firstName());
        }
        if (!request.lastName().isEmpty()) {
            oldCustomer.setLastName(request.lastName());
        }
        if (!request.email().isEmpty()) {
            oldCustomer.setEmail(request.email());
        }
        if (!request.password().isEmpty()) {
            oldCustomer.setPassword(request.password());
        }
        customerRepository.save(oldCustomer);
    }
}
