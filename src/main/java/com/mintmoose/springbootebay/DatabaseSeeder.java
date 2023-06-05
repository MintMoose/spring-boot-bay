package com.mintmoose.springbootebay;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Customer entity1 = new Customer("justshasans55", "Hasan Iqbal",
                "HQball@gmail.com", passwordEncoder.encode("use-hashing"));

        Customer entity2 = new Customer("jamesspyder93", "James Patterson",
                "JP@gmail.com", passwordEncoder.encode("secure?"));

        customerRepository.save(entity1);
        customerRepository.save(entity2);
    }
}
