package com.mintmoose.springbootebay.api.controller;
import com.mintmoose.springbootebay.Controller.CustomerController;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.CustomerDTO;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerProfile_CustomerExists() {
        // Arrange
        String customerUsername = "john_doe";
        Customer customer = new Customer("john_doe", "John Doe", "john@example.com", "password");
        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(customer));

        // Act
        ResponseEntity<?> response = customerController.getCustomerProfile(customerUsername);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerDTO);
        CustomerDTO customerDTO = (CustomerDTO) response.getBody();
        assertEquals(customer.getCustomerId(), customerDTO.getCustomerId());
        assertEquals(customer.getUsername(), customerDTO.getUsername());
        assertEquals(customer.getName(), customerDTO.getName());
    }

    @Test
    void testGetCustomerProfile_CustomerDoesNotExist() {
        // Arrange
        String customerUsername = "non_existent_user";
        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = customerController.getCustomerProfile(customerUsername);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer does not exist.", response.getBody());
    }

    @Test
    void testDeleteCustomer_AuthenticatedUserWithMatchingUsername() {
        // Arrange
        Long customerId = 1L; // Some unique value for the customerId
        String customerUsername = "john_doe";
        Customer authenticatedCustomer = new Customer(customerId, customerUsername, "John Doe", "john@example.com", "password");

        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(authenticatedCustomer));

        // Create a mock authentication object with the authenticated customer and granted authority
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedCustomer, null, authorities);

        // Act
        ResponseEntity<?> response = customerController.deleteCustomer(customerUsername, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully.", response.getBody());
        verify(customerService, times(1)).deleteCustomer(authenticatedCustomer.getCustomerId());
    }

    @Test
    void testDeleteCustomer_AuthenticatedUserWithDifferentUsername() {
        // Arrange
        Long customerId = 1L; // Some unique value for the customerId
        String customerUsername = "john_doe";
        Customer authenticatedCustomer = new Customer(customerId, "another_user", "Another User", "another@example.com", "password");

        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(authenticatedCustomer));

        // Create a mock authentication object with the authenticated customer and granted authority
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedCustomer, null, authorities);

        // Act
        ResponseEntity<?> response = customerController.deleteCustomer(customerUsername, authentication);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access denied. Invalid authorization.", response.getBody());
        verify(customerService, never()).deleteCustomer(anyLong());
    }

    @Test
    void testUpdateCustomer_AuthenticatedUserWithMatchingUsername() {
        // Arrange
        Long customerId = 1L; // Some unique value for the customerId
        String customerUsername = "john_doe";
        Customer authenticatedCustomer = new Customer(customerId, customerUsername, "John Doe", "john@example.com", "password");
        NewCustomerRequest request = new NewCustomerRequest(customerUsername, "John Doe Updated", "updated@example.com", "updatedPassword");

        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(authenticatedCustomer));
        when(customerService.updateCustomer(customerId, request)).thenReturn(authenticatedCustomer);

        // Create a mock authentication object with the authenticated customer
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedCustomer, null, authorities);
        System.out.println(authentication);
        // Act
        ResponseEntity<?> response = customerController.updateCustomer(customerUsername, request, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerDTO);
        CustomerDTO customerDTO = (CustomerDTO) response.getBody();
        assertEquals(authenticatedCustomer.getCustomerId(), customerDTO.getCustomerId());
        System.out.println(request);
        System.out.println(customerDTO);
        assertEquals(authenticatedCustomer.getName(), customerDTO.getName());
        verify(customerService, times(1)).updateCustomer(authenticatedCustomer.getCustomerId(), request);
    }

    @Test
    void testUpdateCustomer_AuthenticatedUserWithDifferentUsername() {
        // Arrange
        Long customerId = 1L; // Some unique value for the customerId
        String customerUsername = "john_doe";
        Customer authenticatedCustomer = new Customer(customerId, "another_user", "Another User", "another@example.com", "password");
        NewCustomerRequest request = new NewCustomerRequest("another_user", "John Doe Updated", "updated@example.com", "updatedPassword");

        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(authenticatedCustomer));

        // Create a mock authentication object with the authenticated customer and granted authority
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedCustomer, null, authorities);

        // Act
        ResponseEntity<?> response = customerController.updateCustomer(customerUsername, request, authentication);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Access denied. Invalid authorization.", response.getBody());
        verify(customerService, never()).updateCustomer(anyLong(), any());
    }


}
