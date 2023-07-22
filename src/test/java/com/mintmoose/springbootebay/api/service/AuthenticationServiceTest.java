package com.mintmoose.springbootebay.api.service;

import com.mintmoose.springbootebay.Config.AuthenticationRequest;
import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import com.mintmoose.springbootebay.Service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class AuthenticationServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticate_SuccessfulAuthentication() {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        String jwtToken = "testjwt";

        AuthenticationRequest request = new AuthenticationRequest(username, password);
        Customer customer = new Customer();
        customer.setUsername(username);

        Mockito.when(customerRepository.findByUsername(anyString())).thenReturn(Optional.of(customer));
        Mockito.when(jwtService.generateToken(customer)).thenReturn(jwtToken);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        // Create mock HttpServletResponse
        HttpServletResponse mockResponse = Mockito.mock(HttpServletResponse.class);

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request, mockResponse);

        // Assert
        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());

        // Verify that cookies were set correctly
        Mockito.verify(mockResponse, Mockito.times(2)).addCookie(Mockito.any(Cookie.class));
    }

    @Test
    void testAuthenticate_InvalidUsername() {
        // Arrange
        String username = "nonexistentuser";
        String password = "testpassword";

        AuthenticationRequest request = new AuthenticationRequest(username, password);

        Mockito.when(customerRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> authenticationService.authenticate(request, this.response));
    }
}
