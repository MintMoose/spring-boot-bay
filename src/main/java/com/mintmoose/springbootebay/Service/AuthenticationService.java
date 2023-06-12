package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Config.AuthenticationRequest;
import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var customer = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username does not exist."));
        var jwtToken = jwtService.generateToken(customer);

        // Set the JWT token as a cookie

        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setSecure(true); // Make sure to use HTTPS // for development htttp.
        cookie.setHttpOnly(true); // Prevent JavaScript access to the cookie
        cookie.setPath("/"); // Set the cookie path
        response.addCookie(cookie);

        String username = customer.getUsername();
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setMaxAge(24 * 60 * 60);
        usernameCookie.setHttpOnly(true); // Prevent JavaScript access to the cookie
        usernameCookie.setPath("/"); // Set the cookie path
        response.addCookie(usernameCookie);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
