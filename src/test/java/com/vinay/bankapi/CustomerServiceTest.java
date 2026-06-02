package com.vinay.bankapi;

import com.vinay.bankapi.models.Customer;
import com.vinay.bankapi.repository.DataStore;
import com.vinay.bankapi.service.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private final CustomerService service = new CustomerService();

    @BeforeEach
    void setUp() {
        DataStore.reset();
    }

    @Test
    void getAllCustomers_returnsThreeCustomers() {
        List<Customer> customers = service.getAllCustomers();
        assertEquals(3, customers.size());
    }

    @Test
    void getCustomerById_returnsCustomerWhenFound() {
        Customer customer = service.getCustomerById(1);
        assertNotNull(customer);
        assertEquals("Vinay Halwan", customer.getName());
    }

    @Test
    void getCustomerById_returnsNullWhenMissing() {
        Customer customer = service.getCustomerById(99);
        assertNull(customer);
    }

    @Test
    void searchCustomersByName_findsMatchingCustomer() {
        List<Customer> results = service.searchCustomersByName("jane");
        assertEquals(1, results.size());
        assertEquals("Jane Smith", results.get(0).getName());
    }

    @Test
    void getPremiumCustomers_returnsOnlyPremiumCustomer() {
        List<Customer> premium = service.getPremiumCustomers();
        assertEquals(1, premium.size());
        assertEquals("Jane Smith", premium.get(0).getName());
    }

    @Test
    void createCustomer_addsNewCustomer() {
        Customer input = new Customer(0, "Test User", "test@email.com");
        Customer created = service.createCustomer(input);

        assertNotNull(created);
        assertEquals("Test User", created.getName());
        assertEquals(4, created.getId());
        assertEquals(4, DataStore.customers.size());
    }

    @Test
    void updateCustomer_updatesExistingCustomer() {
        Customer updated = new Customer(0, "Updated Name", "updated@email.com");
        Customer result = service.updateCustomer(1, updated);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@email.com", result.getEmail());
    }

    @Test
    void deleteCustomer_removesCustomerAndAccounts() {
        boolean deleted = service.deleteCustomer(1);

        assertTrue(deleted);
        assertNull(service.getCustomerById(1));
        assertEquals(2, DataStore.accounts.size());
    }
}