package com.vinay.bankapi.controllers;

import com.vinay.bankapi.models.Customer;
import com.vinay.bankapi.repository.CustomerRepository;
import com.vinay.bankapi.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepo;
    private final AccountRepository accountRepo;

    public CustomerController(CustomerRepository customerRepo,
                              AccountRepository accountRepo) {
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        return customerRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<Customer> getCustomerByName(@RequestParam String name) {
        return customerRepo.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/premium")
    public List<Customer> getPremiumCustomers() {
        return customerRepo.findAll().stream()
                .filter(c -> c.getAccounts()
                        .stream()
                        .mapToDouble(a -> a.getBalance())
                        .sum() > 10000)
                .toList();
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id,
                                                   @RequestBody Customer updated) {
        return customerRepo.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    return ResponseEntity.ok(customerRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        if (!customerRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        customerRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}