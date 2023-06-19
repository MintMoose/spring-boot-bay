package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Service.AddressService;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {

    private final AddressService addressService;
    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<?> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getAddressById(@PathVariable("Id") Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getAddressByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(addressService.getAddressByCustomerId(customerId));
    }
}
