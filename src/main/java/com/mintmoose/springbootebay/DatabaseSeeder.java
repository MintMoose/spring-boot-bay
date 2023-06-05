package com.mintmoose.springbootebay;

import com.mintmoose.springbootebay.Model.Categories;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(CustomerRepository customerRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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

        Product product1 = new Product("Xbox", "For sale: Xbox, never used", 429.99,
                Categories.ELECTRONICS, false, "https://unsplash.com/photos/WMMh6BtmTMo", entity1);

        Product product2 = new Product("Java Teak Garden Bench", "120cm 4ft 2 Seat Chunky Garden Furniture," +
                " They are manufactured form FLEGT certified teak sourced from sustainable sources. Dog not included. stop asking!!!",
                199.99, Categories.HOUSEHOLD, false, "https://unsplash.com/photos/lX-9IaYCals", entity1);

        Product product3 = new Product("Left Hand Taylormade Stealth 2 Driver", "Selling since i thought i was left handed golfer " +
                "( mini golf lefty... ). 10.5 Degree / Stiff Flex Hzrdus Black Gen 4 (camera broke, image not accurate)",
                215, Categories.HOBBY_DIY, true, "https://unsplash.com/photos/NczT3PIpZBw", entity1);

        Product product4 = new Product("Playstation 1", "Playstation 1 (Unopened), never found the time to use it.", 119.99,
                Categories.ELECTRONICS, false, "https://unsplash.com/photos/b-bnM85Z35o", entity2);

        Product product5 = new Product("Space Rock (rarely used)", "(Disclaimer: Not actually from space) French metallurgists discovered that molybdenum," +
                " when alloyed with steel in small quantities, creates a substance that is remarkably tougher than steel alone and is highly resistant to heat." +
                " Ideal for making tools and armor plate. Happy Crafting", 12_899.99,
                Categories.ELECTRONICS, false, "https://unsplash.com/photos/b-bnM85Z35o", entity2);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

    }
}
