package com.mintmoose.springbootebay.api.serviceControllerIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintmoose.springbootebay.Model.Customer;

import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Repos.CustomerRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    public void testGetCustomerDTOByUsername_ReturnsCustomer() throws Exception {
        // Testing Controller + Service Class, Mocking Repo.
        // Prepare test data
        String username = "user1";
        Customer customer = new Customer(1L, "user1", "John Doe", "john@example.com", "password");

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(customer));

        // Perform the HTTP request and validate the response
        mockMvc.perform(get("/customers/user1", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // Testing Controller + Service Class, Mocking Repo.
        Long customerId = 2L;
        String username = "john_doe";

        Customer customer = new Customer(customerId, username, "John Doe", "john@example.com", "password");

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        mockMvc.perform(delete("/test/customers/" + username))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully."));
    }

    @Test
    public void testDeleteCustomerUnauthorised() throws Exception {
        // Testing Controller + Service Class, Mocking Repo.
        Long customerId = 2L;
        String username = "john_doe";

        Customer customer = new Customer(customerId, username, "John Doe", "john@example.com", "password");

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        mockMvc.perform(delete("/test/customers/" + "Joe"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // Testing Controller + Service Class, Mocking Service.
        String username = "user1";
        Customer customer = new Customer(1L, "user1", "John Doe", "john@example.com", "password");
        NewCustomerRequest updatedRequest = new NewCustomerRequest("user1", "Updated John Doe", "updated_john@example.com", "new_password");

        when(customerRepository.findByUsername(username)).thenReturn(Optional.of(customer));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        mockMvc.perform(put("/test/customers/" + username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.name").value("Updated John Doe"));

    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

