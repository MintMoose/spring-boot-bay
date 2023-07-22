package com.mintmoose.springbootebay.api.service;

import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Config.RegisterRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Role;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_SuccessfulRegistration() {
        // Arrange
        String username = "testuser";
        String name = "Test User";
        String email = "testuser@example.com";
        String password = "testpassword";
        String encodedPassword = "encodedTestPassword";
        String jwtToken = "testjwt";

        RegisterRequest request = new RegisterRequest(username, name, email, password);
        Customer customer = Customer.builder()
                .username(username)
                .name(name)
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.when(customerRepository.save(customerCaptor.capture())).thenAnswer(invocation -> {
            // Get the captured customer object
            Customer capturedCustomer = customerCaptor.getValue();

            // Use reflection to set the customerId manually (assuming it's a Long type)
            Field customerIdField = Customer.class.getDeclaredField("customerId");
            customerIdField.setAccessible(true);
            customerIdField.set(capturedCustomer, 1L); // Set the ID to any desired value

            return capturedCustomer;
        });

        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Set up JwtService mock to return the desired JWT token when generateToken is called with the capturedCustomer
        Mockito.when(jwtService.generateToken(Mockito.any(Customer.class))).thenReturn(jwtToken);

        // Act
        AuthenticationResponse response = registrationService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());

        // Use the capturedCustomer to access the customerId
        Customer capturedCustomer = customerCaptor.getValue();
        Long customerId = capturedCustomer.getCustomerId();

        // Assert or perform further verifications using customerId
        assertNotNull(customerId);
        // For example, you can verify that the customerId is correctly set to the expected value
        assertEquals(1L, customerId);

        // Verify that the customer was saved with the correct details
        Mockito.verify(customerRepository).save(Mockito.argThat(savedCustomer ->
                savedCustomer.getUsername().equals(username)
                        && savedCustomer.getName().equals(name)
                        && savedCustomer.getEmail().equals(email)
                        && savedCustomer.getRole() == Role.USER
                        && savedCustomer.getPassword().equals(encodedPassword)));
    }


    @Test
    void testRegister_NullOrEmptyParameters() {
        // Arrange
        RegisterRequest request1 = new RegisterRequest(null, "Test User", "testuser@example.com", "testpassword");
        RegisterRequest request2 = new RegisterRequest("testuser", null, "testuser@example.com", "testpassword");
        RegisterRequest request3 = new RegisterRequest("testuser", "Test User", null, "testpassword");
        RegisterRequest request4 = new RegisterRequest("testuser", "Test User", "testuser@example.com", null);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(request1));
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(request2));
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(request3));
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(request4));
    }
}
