package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @PostMapping("")
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        customerService.createCustomer(request);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public void updateCustomer(@PathVariable Long customerId, @RequestBody NewCustomerRequest request) {
        customerService.updateCustomer(customerId, request);
    }
}
