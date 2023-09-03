package com.mintmoose.springbootebay;

import com.mintmoose.springbootebay.Model.*;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Repos.OrderRepository;
import com.mintmoose.springbootebay.Repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
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

        // EXTRA
        Customer entity3 = new Customer("electric", "Emily Thompson",
                "ziggy.thunderstrike@example.com", passwordEncoder.encode("pass"));

        Customer entity4 = new Customer("MysteryWalker", "Oliver Mitchell",
                "oliver.mitchell@example.com", passwordEncoder.encode("Galaxy456#"));

        Customer entity5 = new Customer("tealover789", "Lily Evans",
                "lily.evans@example.com", passwordEncoder.encode("TeaLover!789"));


        customerRepository.save(entity3);
        customerRepository.save(entity4);
        customerRepository.save(entity5);

        // Seed Product from file
        try {
            Resource resource = new ClassPathResource("product.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> fields = Arrays.asList(line.split("\\|"));
                if (fields.size() == 6) {
                    String productName = fields.get(0);
                    String description = fields.get(1);
                    double price = Double.parseDouble(fields.get(2));
                    Categories category = Categories.valueOf(fields.get(3));
                    String imageUrl = fields.get(4);
                    String username = fields.get(5);



                    if (StringUtils.hasText(productName) && StringUtils.hasText(description)
                            && price > 0 && category != null && StringUtils.hasText(imageUrl)
                            && StringUtils.hasText(username)) {

                        Product product = new Product(productName, description, price, category, false, imageUrl, username);
                        productRepository.save(product);

                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Address address1 = new Address(1L, "96", "Euston Road", "London", "United Kingdom", "NW1 2DB");
        Address address2 = new Address(2L, "710", "S Ocean Blvd", "Palm Beach, Florida", "United States of America", "33480");

        addressRepository.save(address2);
        addressRepository.save(address1);

        Product product3 = new Product("Left Hand Taylormade Stealth 2 Driver", "Selling since i thought i was left handed golfer " +
                "( mini golf lefty... ). 10.5 Degree / Stiff Flex Hzrdus Black Gen 4 (camera broke, stock image)",
                215, Categories.HOBBY_DIY, true, "https://source.unsplash.com/NczT3PIpZBw", "justshasans55");

        productRepository.save(product3);
        Order order1 = new Order(2L, product3, product3.getPrice(), 1L);

        orderRepository.save(order1);


    }
}
