package com.vinay.bankapi.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "customers")
public class Customer {
    @Id
    private int id;
    private String name;
    private String email;
    private List<Account> accounts;

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    public int getId() { 
        return id; 
    }

    public String getName() { 
        return name; 
    }

    public String getEmail() { 
        return email; 
    }

    public List<Account> getAccounts() { 
        return accounts; 
    }


    public void setName(String name) { 
        this.name = name; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
}