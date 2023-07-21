package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @GetMapping("/{customerUsername}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String customerUsername) {
        Optional<Customer> theCustomer = customerService.getCustomerByUsername(customerUsername);
        if (theCustomer.isPresent()) {
                return ResponseEntity.ok(theCustomer.get().toDTO());
            }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer does not exist.");
    }


    @DeleteMapping("/{customerUsername}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerUsername, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElse(null); // Use null instead of throwing an exception
            if (requestCustomer == null || !requestCustomer.getUsername().equals(customerUsername)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
            }
            try {
                if (Objects.equals(customerUsername, username)) {
                    customerService.deleteCustomer(requestCustomer.getCustomerId());
                    return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }

    @PutMapping("/{customerUsername}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customerUsername, @RequestBody NewCustomerRequest request, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println(username);
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElse(null); // Use null instead of throwing an exception
            if (requestCustomer == null || !requestCustomer.getUsername().equals(customerUsername)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
            }
            System.out.println(requestCustomer);
            Customer updatedCustomer = customerService.updateCustomer(requestCustomer.getCustomerId(), request);
            System.out.println(updatedCustomer);
            return ResponseEntity.ok(updatedCustomer.toDTO());
//          return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully.");
        }
        return ResponseEntity.notFound().build();
    }

}

