package com.mintmoose.springbootebay;

import com.mintmoose.springbootebay.Model.*;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Repos.OrderRepository;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository, ProductRepository productRepository, AddressRepository addressRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        Customer entity1 = new Customer("justshasans55", "Hasan Iqbal",
                "HQball@gmail.com", passwordEncoder.encode("use-hashing"));

        Customer entity2 = new Customer("jamesspyder93", "James Patterson",
                "JP@gmail.com", passwordEncoder.encode("dRea%nTie6Holve"));

        Customer admin1 = new Customer("admin", "Ashok Kumar", "admin@hotmail.co.uk", passwordEncoder.encode("secure?"));
        admin1.setRole(Role.ADMIN);

        customerRepository.save(entity1);
        customerRepository.save(entity2);
        customerRepository.save(admin1);

        Product product1 = new Product("Xbox (New)", "For sale: Xbox, never used", 429.99,
                Categories.ELECTRONICS, false, "https://source.unsplash.com/WMMh6BtmTMo", 1L);

        Product product2 = new Product("Java Teak Garden Bench", "120cm 4ft 2 Seat Chunky Garden Furniture," +
                " They are manufactured form FLEGT certified teak sourced from sustainable sources. Dog not included. stop asking!!!",
                199.99, Categories.HOUSEHOLD, false, "https://source.unsplash.com/lX-9IaYCals", 1L);

        Product product3 = new Product("Left Hand Taylormade Stealth 2 Driver", "Selling since i thought i was left handed golfer " +
                "( mini golf lefty... ). 10.5 Degree / Stiff Flex Hzrdus Black Gen 4 (camera broke, stock image)",
                215, Categories.HOBBY_DIY, true, "https://source.unsplash.com/NczT3PIpZBw", 1L);

        Product product4 = new Product("Playstation 1", "Playstation 1 (Unopened), never found the time to use it.", 119.99,
                Categories.ELECTRONICS, false, "https://source.unsplash.com/b-bnM85Z35o", 2L);

        Product product5 = new Product("Space Rock (rarely used)", "(Disclaimer: Not actually from space) French metallurgists discovered that molybdenum," +
                " when alloyed, creates a substance that is remarkably tougher than steel alone and is highly resistant to heat." +
                " Ideal for making tools and armor plate. Happy Crafting!", 17899.99,
                Categories.HEALTH_BEAUTY, false, "https://source.unsplash.com/9kRjMMLSPqw", 2L);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

        Address address1 = new Address(1L, "96", "Euston Road", "London", "United Kingdom", "NW1 2DB");
        Address address2 = new Address(2L, "710", "S Ocean Blvd", "Palm Beach, Florida", "United States of America", "33480");

        addressRepository.save(address2);
        addressRepository.save(address1);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product3);

        Order order1 = new Order(2L, productList, product3.getPrice() + product1.getPrice(), 1L);


        orderRepository.save(order1);

    }
}
