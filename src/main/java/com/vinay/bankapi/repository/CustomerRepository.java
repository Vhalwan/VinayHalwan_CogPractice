package com.vinay.bankapi.repository;

import com.vinay.bankapi.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {

    List<Customer> findByNameContainingIgnoreCase(String name);
}