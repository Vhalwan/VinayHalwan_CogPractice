package com.vinay.bankapi.service;

import com.vinay.bankapi.models.Account;
import com.vinay.bankapi.models.Customer;
import com.vinay.bankapi.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    public List<Customer> getAllCustomers() {
        return DataStore.customers;
    }

    public Customer getCustomerById(int id) {
        for (Customer c : DataStore.customers) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> results = new ArrayList<>();
        for (Customer c : DataStore.customers) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(c);
            }
        }
        return results;
    }

    public List<Customer> getPremiumCustomers() {
        List<Customer> premium = new ArrayList<>();

        for (Customer c : DataStore.customers) {
            double total = 0;
            for (Account a : c.getAccounts()) {
                total += a.getBalance();
            }
            if (total > 10000) {
                premium.add(c);
            }
        }

        return premium;
    }

    public Customer createCustomer(Customer customer) {
        int newId = DataStore.customers.stream()
                .mapToInt(Customer::getId)
                .max()
                .orElse(0) + 1;

        Customer newCustomer = new Customer(newId, customer.getName(), customer.getEmail());
        DataStore.customers.add(newCustomer);
        return newCustomer;
    }

    public Customer updateCustomer(int id, Customer updated) {
        Customer existing = getCustomerById(id);
        if (existing == null) {
            return null;
        }

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return existing;
    }

    public boolean deleteCustomer(int id) {
        Customer existing = getCustomerById(id);
        if (existing == null) {
            return false;
        }

        DataStore.accounts.removeIf(a -> a.getCustomerId() == id);
        DataStore.customers.remove(existing);
        return true;
    }
}