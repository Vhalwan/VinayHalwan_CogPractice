package com.vinay.bankapi.models;

public class Account {
    private int id;
    private String accountNumber;
    private String accountType;
    private double balance;
    private int customerId;

    public Account(int id, String accountNumber, String accountType, double balance, int customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customerId = customerId;
    }

    public int getId() { 
        return id; 
    }

    public String getAccountNumber() { 
        return accountNumber; 
    }

    public String getAccountType() { 
        return accountType; 
    }

    public double getBalance() { 
        return balance; 
    }

    public int getCustomerId() {
        return customerId; 
    }


    public void setAccountType(String accountType) { 
        this.accountType = accountType; 
    }

    public void setBalance(double balance) { 
        this.balance = balance; 
    }
}