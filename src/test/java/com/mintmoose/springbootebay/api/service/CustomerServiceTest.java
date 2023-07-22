package com.mintmoose.springbootebay.api.service;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void getAllCustomers_ValidPageable_ReturnsCustomers() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Customer> mockCustomers = Arrays.asList(
                new Customer("user1", "User 1", "user1@example.com", "password1"),
                new Customer("user2", "User 2", "user2@example.com", "password2")
        );
        Page<Customer> mockCustomerPage = new PageImpl<>(mockCustomers);

        when(customerRepository.findAllCustomers(pageable)).thenReturn(mockCustomerPage);

        // Act
        Page<Customer> result = customerService.getAllCustomers(pageable);

        // Assert
        Assertions.assertEquals(2, result.getTotalElements());
        Assertions.assertEquals(mockCustomers, result.getContent());
    }

    @Test
    public void getCustomer_ExistingCustomerId_ReturnsCustomer() {
        // Arrange
        Long customerId = 1L;
        Customer mockCustomer = new Customer("user1", "User 1", "user1@example.com", "password1");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        // Act
        Optional<Customer> result = customerService.getCustomer(customerId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockCustomer, result.get());
    }

    @Test
    public void getCustomerByUsername_ExistingUsername_ReturnsCustomer() {
        // Arrange
        String username = "user1";
        Customer mockCustomer = new Customer("user1", "User 1", "user1@example.com", "password1");
        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(mockCustomer));

        // Act
        Optional<Customer> result = customerService.getCustomerByUsername(username);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockCustomer, result.get());
    }

    @Test
    public void getCustomerByUsername_NonExistentUsername_ReturnsEmptyOptional() {
        // Arrange
        String username = "nonexistentuser";
        when(customerRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.getCustomerByUsername(username);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getCustomer_NonExistentCustomerId_ReturnsEmptyOptional() {
        // Arrange
        Long customerId = 100L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.getCustomer(customerId);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }


    @Test
    public void createCustomer_ValidRequest_CustomerSaved() {
        // Arrange
        NewCustomerRequest request = new NewCustomerRequest("username", "John Doe", "john@example.com", "password");

        // Act
        customerService.createCustomer(request);

        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class));
        // Additional verification to check that the correct customer is saved
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        Customer savedCustomer = customerCaptor.getValue();
        Assertions.assertEquals(request.username(), savedCustomer.getUsername());
        Assertions.assertEquals(request.name(), savedCustomer.getName());
        Assertions.assertEquals(request.email(), savedCustomer.getEmail());
        Assertions.assertEquals(request.password(), savedCustomer.getPassword());
    }

    @Test
    public void updateCustomer_ValidCustomerId_CustomerUpdated() {
        // Arrange
        Long customerId = 1L;
        Customer existingCustomer = new Customer("old_username", "Old Name", "old@example.com", "old_password");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        NewCustomerRequest request = new NewCustomerRequest("new_username", "New Name", "new@example.com", "new_password");

        // Act
        Customer updatedCustomer = customerService.updateCustomer(customerId, request);

        // Use ArgumentCaptor to capture the argument passed to save()
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());

        // Get the captured Customer object
        Customer capturedCustomer = customerCaptor.getValue();

        // Assert
        Assertions.assertEquals(request.name(), capturedCustomer.getName());
        Assertions.assertEquals(request.email(), capturedCustomer.getEmail());
        Assertions.assertEquals(request.password(), capturedCustomer.getPassword());
    }

    @Test
    public void updateCustomer_NonExistentCustomerId_ExceptionThrown() {
        // Arrange
        Long customerId = 100L;
        NewCustomerRequest request = new NewCustomerRequest("new_username", "New Name", "new@example.com", "new_password");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(customerId, request));
    }

    @Test
    public void deleteCustomer_ValidCustomerId_CustomerDeleted() {
        // Arrange
        Long customerId = 1L;

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).deleteById(customerId);
    }

}
