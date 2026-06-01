public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, Customer accountHolder, double balance, double interestRate) {
        super(accountNumber, accountHolder, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < 100) {
            System.out.println("Cannot withdraw! Balance cannot go below $100.");
        } else {
            setBalance(getBalance() - amount);
            System.out.println("Withdrew $" + amount);
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("      SAVINGS ACCOUNT RECEIPT");
        System.out.println("Account #  : " + getAccountNumber());
        System.out.println("Holder     : " + getAccountHolder().getFirstName() + " " + getAccountHolder().getLastName());
        System.out.println("Balance    : $" + getBalance());
        System.out.println("Interest   : " + (interestRate * 100) + "%");
    }
}