package com.mintmoose.springbootebay.Controller;

import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewCustomerRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/customer")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdminCustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer"));
        return ResponseEntity.ok(customer);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        // Todo: since you cant have same username or email in repo this needs to be return the correct response.
        customerService.createCustomer(request);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer"));
        customerService.deleteCustomer(customer.getCustomerId());
        ResponseEntity.ok("Customer deleted successfully.");
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody NewCustomerRequest request) {
        Customer customer = customerService.getCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("No such customer"));
        Customer updatedCustomer =  customerService.updateCustomer(customerId, request);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        }
        return ResponseEntity.notFound().build();

    }
}
