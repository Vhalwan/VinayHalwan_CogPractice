public abstract class Account implements ITransaction {
    private String accountNumber;
    private Customer accountHolder;
    private double balance;

    public Account(String accountNumber, Customer accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber; 
    }
    
    public Customer getAccountHolder() { 
        return accountHolder;
    }

    public double getBalance() { 
        return balance; 
    }

    public void setBalance(double balance) { 
        this.balance = balance; 
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance += amount;
        System.out.println("Deposited $" + amount);
    }

    public abstract void withdraw(double amount);
}
