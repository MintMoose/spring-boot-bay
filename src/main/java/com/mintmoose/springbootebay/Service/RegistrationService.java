package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Config.AuthenticationResponse;
import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Config.RegisterRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Role;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {

        if (request.getUsername() == null || request.getUsername().isEmpty()
                || request.getName() == null || request.getName().isEmpty()
                || request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("All request parameters are required.");
        }

        var customer = Customer.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}


