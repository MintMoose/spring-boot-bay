package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @GetMapping("/{customerUsername}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String customerUsername, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        if (jwtService.isTokenValid(token, requestCustomer)) {
            return ResponseEntity.ok(requestCustomer);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorisation.");
    }

    @DeleteMapping("/{customerUsername}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerUsername, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
        if (jwtService.isTokenValid(token, requestCustomer)) {
            customerService.deleteCustomer(requestCustomer.getCustomerId());
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorisation.");
    }

    @PutMapping("/{customerUsername}")
    public ResponseEntity<String> updateCustomer(@PathVariable String customerUsername, @RequestBody NewCustomerRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElseThrow(() -> new IllegalArgumentException("No such customer exists."));

        if (jwtService.isTokenValid(token, requestCustomer)) {
            customerService.updateCustomer(requestCustomer.getCustomerId(), request);
            return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorisation.");
    }
}
