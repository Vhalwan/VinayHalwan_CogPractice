public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, Customer accountHolder, double balance, double overdraftLimit) {
        super(accountNumber, accountHolder, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() { return overdraftLimit; }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < -overdraftLimit) {
            System.out.println("Exceeds overdraft limit of $" + overdraftLimit);
        } else {
            setBalance(getBalance()-amount);
            System.out.println("Withdrew $" + amount);
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("    CHECKING ACCOUNT RECEIPT");
        System.out.println("Account #  : " + getAccountNumber());
        System.out.println("Holder     : " + getAccountHolder().getFirstName() + " " + getAccountHolder().getLastName());
        System.out.println("Balance    : $" + getBalance());
        System.out.println("Overdraft  : $" + overdraftLimit);
    }
}