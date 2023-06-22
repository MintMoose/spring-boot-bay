package com.mintmoose.springbootebay.Controller.Admin;

import com.mintmoose.springbootebay.Service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/address")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminAddressController {

    private final AddressService addressService;

    @GetMapping()
    public ResponseEntity<?> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
}
