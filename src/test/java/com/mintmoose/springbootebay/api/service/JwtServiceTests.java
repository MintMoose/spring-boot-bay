package com.mintmoose.springbootebay.api.service;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {

    @Mock
    private JwtService jwtService;


    @Test
    void testExtractUsername_ValidToken() {
        String jwtToken = "validJwtToken";
        String username = "testuser";

        when(jwtService.extractUsername(jwtToken)).thenReturn(username);

        String extractedUsername = jwtService.extractUsername(jwtToken);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testExtractUsername_InvalidToken() {
        String jwtToken = "invalidJwtToken";

        when(jwtService.extractUsername(jwtToken)).thenThrow(new MalformedJwtException("Invalid token"));

        assertThrows(MalformedJwtException.class, () -> jwtService.extractUsername(jwtToken));
    }

    @Test
    void testGenerateToken() {
        String username = "testuser";
        Customer customer = new Customer();
        customer.setUsername(username);

        String jwtToken = "generatedJwtToken";

        when(jwtService.generateToken(customer)).thenReturn(jwtToken);

        String generatedToken = jwtService.generateToken(customer);

        assertEquals(jwtToken, generatedToken);
    }



    @Test
    void testIsTokenValid_ExpiredToken() {
        String jwtToken = "expiredJwtToken";
        String username = "testuser";

        // Create a collection of granted authorities (it can be empty)
        Collection<GrantedAuthority> authorities = Collections.emptyList();

        User userDetails = new User(username, "password", authorities);

        boolean isValid = jwtService.isTokenValid(jwtToken, userDetails);

        assertFalse(isValid);
    }

    @Test
    void testCreateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("key1", "value1");
        String username = "testuser";
        Collection<GrantedAuthority> authorities = Collections.emptyList();

        User userDetails = new User(username, "password", authorities);
        String jwtToken = "generatedJwtToken";

        when(jwtService.createToken(claims, userDetails)).thenReturn(jwtToken);

        String generatedToken = jwtService.createToken(claims, userDetails);

        assertEquals(jwtToken, generatedToken);
    }

    @Test
    void testIsTokenExpired_ValidToken() {
        String jwtToken = "validJwtToken";

        boolean isExpired = jwtService.isTokenExpired(jwtToken);

        assertFalse(isExpired);
    }

//    @Test
//    void testIsTokenExpired_ExpiredToken() {
//    }
//
//    @Test
//    void testIsTokenValid_ValidToken() {
//    }

}
