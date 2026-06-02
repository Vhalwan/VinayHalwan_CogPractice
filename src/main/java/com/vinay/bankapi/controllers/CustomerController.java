package com.vinay.bankapi.controllers;

import com.vinay.bankapi.models.Customer;
import com.vinay.bankapi.repository.DataStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @GetMapping
    public List<Customer> getAllCustomers() {
        return DataStore.customers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        for (Customer c : DataStore.customers) {
            if (c.getId() == id) {
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> getCustomerByName(@RequestParam String name) {
        List<Customer> results = new ArrayList<>();
        for (Customer c : DataStore.customers) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(c);
            }
        }
        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/premium")
    public List<Customer> getPremiumCustomers() {
        List<Customer> premium = new ArrayList<>();
        for (Customer c : DataStore.customers) {
            double total = 0;
            for (var a : c.getAccounts()) {
                total += a.getBalance();
            }
            if (total > 10000) {
                premium.add(c);
            }
        }
        return premium;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        int newId = DataStore.customers.stream()
                .mapToInt(Customer::getId)
                .max()
                .orElse(0) + 1;

        Customer newCustomer = new Customer(newId, customer.getName(), customer.getEmail());
        DataStore.customers.add(newCustomer);
        return ResponseEntity.status(201).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer updated) {
        for (Customer c : DataStore.customers) {
            if (c.getId() == id) {
                c.setName(updated.getName());
                c.setEmail(updated.getEmail());
                return ResponseEntity.ok(c);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
        Customer target = null;

        for (Customer c : DataStore.customers) {
            if (c.getId() == id) {
                target = c;
                break;
            }
        }

        if (target == null) {
            return ResponseEntity.notFound().build();
        }

        DataStore.accounts.removeIf(a -> a.getCustomerId() == id);
        DataStore.customers.remove(target);
        return ResponseEntity.ok("Customer " + id + " deleted successfully.");
    }
}