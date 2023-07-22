package com.mintmoose.springbootebay.api.repository;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    public String customer1Username = "Shark123";

    @BeforeEach
    public void setUp() {
        Customer customer1 = new Customer(customer1Username, "Sarah Johnson",
                "sarah.johnson@example.com", "GameOn456#");
        Customer customer2 = new Customer("NatureLover99", "John Smith",
                "john.smith@example.com", "GreenThumb123!");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    public void customerRepository_SaveAll_ReturnsSavedCustomer() {
        // Arrange
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                "ziggy.thunderstrike@example.com", "Lightning789$");

        // Act
        Customer savedCustomer = customerRepository.save(newCustomer);

        // Assert
        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer.getCustomerId()).isGreaterThan(0);

    }

    @Test
    public void customerRepository_GetAll_ReturnsMoreThanOneCustomer() {
        List<Customer> customerList = customerRepository.findAll();

        Assertions.assertThat(customerList).isNotNull();
        Assertions.assertThat(customerList.size()).isGreaterThanOrEqualTo(2);

    }

    @Test
    public void customerRepository_FindById_ReturnsCustomerNotNull() {
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                "ziggy.thunderstrike@example.com", "Lightning789$");

        Customer savedCustomer = customerRepository.save(newCustomer);

        Long customerId = savedCustomer.getCustomerId();

        Customer customerReturn = customerRepository.findById(customerId).get();

        Assertions.assertThat(customerReturn).isNotNull();
        Assertions.assertThat(customerReturn.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    public void customerRepository_FindByUsername_ReturnsCustomerNotNull() {

        Customer customerReturn = customerRepository.findByUsername(customer1Username).get();

        Assertions.assertThat(customerReturn).isNotNull();

    }

    @Test
    public void customerRepository_UpdateCustomer_ReturnsCustomerNotNull() {
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                "ziggy.thunderstrike@example.com", "Lightning789$");

        customerRepository.save(newCustomer);

        Long savedCustomerId = newCustomer.getCustomerId();

        Customer savedCustomer = customerRepository.findById(savedCustomerId).get();

        savedCustomer.setUsername("ArtificialLover01");


        Customer updatedCustomer = customerRepository.save(savedCustomer);

        Customer updatedCustomerReturn = customerRepository.findById(updatedCustomer.getCustomerId()).get();

        Assertions.assertThat(updatedCustomerReturn).isNotNull();
        Assertions.assertThat(updatedCustomerReturn.getUsername()).isNotNull();

    }

    @Test
    public void customerRepository_UpdateCustomer_ReturnsUpdatedCustomer() {
        String oldEmail = "ziggy.thunderstrike@example.com";
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                oldEmail, "Lightning789$");

        customerRepository.save(newCustomer);

        Long savedCustomerId = newCustomer.getCustomerId();

        Customer savedCustomer = customerRepository.findById(savedCustomerId).get();

        String newUsername = "ArtificialLover01";

        savedCustomer.setUsername(newUsername);
        savedCustomer.setName("Teddy Smith");


        Customer updatedCustomer = customerRepository.save(savedCustomer);

        Customer updatedCustomerReturn = customerRepository.findById(updatedCustomer.getCustomerId()).get();

        Assertions.assertThat(updatedCustomerReturn).isNotNull();
        Assertions.assertThat(updatedCustomerReturn.getCustomerId()).isEqualTo(savedCustomerId);
        Assertions.assertThat(updatedCustomerReturn.getUsername()).isEqualTo(newUsername);
        Assertions.assertThat(updatedCustomerReturn.getEmail()).isEqualTo(oldEmail);
    }

    @Test
    public void customerRepository_DeleteById_ReturnsEmptyCustomerAfterCheckingExistence() {
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                "ziggy.thunderstrike@example.com", "Lightning789$");

        customerRepository.save(newCustomer);

        Optional<Customer> customerReturn = customerRepository.findById(newCustomer.getCustomerId());
        Assertions.assertThat(customerReturn).isNotEmpty();

        customerRepository.deleteById(newCustomer.getCustomerId());
        customerReturn = customerRepository.findById(newCustomer.getCustomerId());

        Assertions.assertThat(customerReturn).isEmpty();

    }


    @Test
    public void customerRepository_DeleteNonExistentCustomer_ReturnsNoException() {
        long nonExistentCustomerId = 999999L;

        Assertions.assertThatCode(() -> customerRepository.deleteById(nonExistentCustomerId))
                .doesNotThrowAnyException();
    }


    @Test
    public void customerRepository_CustomQuery_FindByUsername() {
        Customer newCustomer = new Customer("ElectricJellyfish88", "Emily Thompson",
                "ziggy.thunderstrike@example.com", "Lightning789$");

        customerRepository.save(newCustomer);

        Optional<Customer> customerReturn = customerRepository.findByUsername(newCustomer.getUsername());

        Assertions.assertThat(customerReturn).isNotEmpty();
        Assertions.assertThat(customerReturn.get().getUsername()).isEqualTo(newCustomer.getUsername());
    }





}
