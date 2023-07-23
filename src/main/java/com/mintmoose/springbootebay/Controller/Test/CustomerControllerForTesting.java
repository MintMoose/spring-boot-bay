package com.mintmoose.springbootebay.Controller.Test;

import com.mintmoose.springbootebay.Config.JwtService;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test/customers")
@AllArgsConstructor
public class CustomerControllerForTesting {

    private final CustomerService customerService;
    private final JwtService jwtService;

    @DeleteMapping("/{customerUsername}")
    public ResponseEntity<?> deleteCustomerTest(@PathVariable String customerUsername) {

        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElse(null); // Use null instead of throwing an exception
        if (requestCustomer == null || !requestCustomer.getUsername().equals(customerUsername)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
        }
        try {

                customerService.deleteCustomer(requestCustomer.getCustomerId());
                return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{customerUsername}")
    public ResponseEntity<?> updateCustomerTest(@PathVariable String customerUsername, @RequestBody NewCustomerRequest request) {

        Customer requestCustomer = customerService.getCustomerByUsername(customerUsername)
                .orElse(null); // Use null instead of throwing an exception
        if (requestCustomer == null || !requestCustomer.getUsername().equals(customerUsername)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");
        }
        System.out.println(requestCustomer);
        Customer updatedCustomer = customerService.updateCustomer(requestCustomer.getCustomerId(), request);
        System.out.println(updatedCustomer);
        return ResponseEntity.ok(updatedCustomer.toDTO());
    }

}

