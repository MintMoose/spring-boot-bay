package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.NewAddressRequest;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.AddressService;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {

    private final AddressService addressService;
    private final CustomerService customerService;


    @GetMapping("/{Id}")
    public ResponseEntity<?> getAddressById(@PathVariable("Id") Long id, Authentication authentication) {
        Long customerId = addressService.getAddressById(id).getCustomerId();
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer requestCustomer = customerService.getCustomerByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Access denied. Invalid authorization."));
            Customer idCustomer = customerService.getCustomerById(customerId)
                    .orElseThrow( () -> new IllegalArgumentException("Id is wrong"));
            try {
                if (Objects.equals(requestCustomer, idCustomer)) {
                    return ResponseEntity.ok(addressService.getAddressById(id));
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getAddressByCustomerId(@PathVariable("customerId") Long customerId, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer variableCustomer = customerService.getCustomerById(customerId).orElseThrow(() -> new IllegalArgumentException("Id doesn't match!"));
            try {
                if (Objects.equals(variableCustomer.getUsername(), username)) {
                    return ResponseEntity.ok(addressService.getAddressByCustomerId(customerId));
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");

    }

    @PostMapping("/{customerId}")
    public ResponseEntity<?> createAddress(@RequestBody NewAddressRequest request, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            Customer variableCustomer = customerService.getCustomerByUsername(username).orElseThrow(() -> new IllegalArgumentException("Id doesn't match!"));
            try {
                    Address createdAddress = addressService.createAddress(request);
                    return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. Invalid authorization.");

    }
}
