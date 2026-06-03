package com.vinay.bankapi.repository;

import com.vinay.bankapi.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, Integer> {

    List<Account> findByCustomerId(int customerId);
}