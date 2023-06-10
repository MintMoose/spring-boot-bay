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
@RequestMapping("/customers")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @GetMapping("/{customerUsername}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String customerUsername, @RequestHeader("Authorization") String authorizationHeader) {
        return processCustomerRequest(customerUsername, authorizationHeader, (token, requestCustomer) -> {
            return ResponseEntity.ok(requestCustomer);
        });
    }

    @DeleteMapping("/{customerUsername}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerUsername, @RequestHeader("Authorization") String authorizationHeader) {
        return processCustomerRequest(customerUsername, authorizationHeader, (token, requestCustomer) -> {
            customerService.deleteCustomer(requestCustomer.getCustomerId());
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
        });
    }

    @PutMapping("/{customerUsername}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customerUsername, @RequestBody NewCustomerRequest request, @RequestHeader("Authorization") String authorizationHeader) {
        return processCustomerRequest(customerUsername, authorizationHeader, (token, requestCustomer) -> {
            Customer updatedCustomer = customerService.updateCustomer(requestCustomer.getCustomerId(), request);
            if (updatedCustomer != null) {
                return ResponseEntity.ok(updatedCustomer);
//                return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully.");
            }
            return ResponseEntity.notFound().build();

        });
    }

    private ResponseEntity<?> processCustomerRequest(String customerUsername, String authorizationHeader, CustomerRequestProcessor processor) {
        String token = authorizationHeader.substring(7);
        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));

        if (jwtService.isTokenValid(token, requestCustomer)) {
            return processor.process(token, requestCustomer);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
    }

    private interface CustomerRequestProcessor {
        ResponseEntity<?> process(String token, Customer requestCustomer);
    }
}

