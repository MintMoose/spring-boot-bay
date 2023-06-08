package com.mintmoose.springbootebay.Controller;


import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerProfileController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @GetMapping("")
    public String getAnything() {
        return "Authorisation?";
    }

    //Todo: Make sure the client includes the token correctly in the header.
    //Todo: returning specific error responses with appropriate HTTP status codes instead of generic exceptions.
    @GetMapping("/{customerUsername}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String customerUsername, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Username does not match."));
        System.out.println(requestCustomer);
        System.out.println(token);
        if (jwtService.isTokenValid(token, requestCustomer)) {
            return ResponseEntity.ok(requestCustomer);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}

