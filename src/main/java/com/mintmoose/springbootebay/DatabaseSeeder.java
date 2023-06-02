package com.mintmoose.springbootebay;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public DatabaseSeeder(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    @Override
    public void run(String... args) {

        Customer entity1 = new Customer("Hasan", "Iqbal", "HQball@gmail.com", "use-hashing");

        Customer entity2 = new Customer("James", "Patterson", "JP@gmail.com", "secure?");

        customerRepository.save(entity1);
        customerRepository.save(entity2);
    }
}
