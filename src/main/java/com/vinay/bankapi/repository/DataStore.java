package com.vinay.bankapi.repository;

import com.vinay.bankapi.models.Account;
import com.vinay.bankapi.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static List<Customer> customers = new ArrayList<>();
    public static List<Account> accounts = new ArrayList<>();

    static {
        Customer c1 = new Customer(1, "Vinay Halwan", "vinay@email.com");
        Customer c2 = new Customer(2, "John Doe", "john@email.com");
        Customer c3 = new Customer(3, "Jane Smith", "jane@email.com");

        Account a1 = new Account(1, "SA001", "Savings", 1500.00, 1);
        Account a2 = new Account(2, "CA001", "Checking", 800.00, 2);
        Account a3 = new Account(3, "SA002", "Savings", 12000.00, 3);

        c1.getAccounts().add(a1);
        c2.getAccounts().add(a2);
        c3.getAccounts().add(a3);

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        accounts.add(a1);
        accounts.add(a2);
        accounts.add(a3);
    }
}