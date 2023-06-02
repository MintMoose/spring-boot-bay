package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("customer")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @PostMapping("sign-up")
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        customerService.createCustomer(request);
    }

    @DeleteMapping("customer/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    public void updateCustomer(@PathVariable Long customerId, @RequestBody NewCustomerRequest request) {
        customerService.updateCustomer(customerId, request);
    }
}
