package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Config.AuthenticationRequest;
import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
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


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var customer = customerRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username does not exist."));
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
