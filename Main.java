import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Customer> customers = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedData();
        
        while (true) {
            System.out.println("     BANK APP ");
            System.out.println("1. Create Account");
            System.out.println("2. View All Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. Close Account");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1": createAccount(); break;
                case "2": viewAccounts(); break;
                case "3": deposit(); break;
                case "4": withdraw(); break;
                case "5": transfer(); break;
                case "6": closeAccount(); break;
                case "7":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid, try again.");
            }
        }
    }

    static void seedData() {
        Customer c1 = new Customer("C001", "John", "Doe");
        Customer c2 = new Customer("C002", "Mike", "Josh");

        SavingsAccount s1 = new SavingsAccount("SA001", c1, 1000.0, 0.05);
        CheckingAccount ch1 = new CheckingAccount("CA001", c2, 500.0, 200.0);

        c1.getAccounts().add(s1);
        c2.getAccounts().add(ch1);

        customers.add(c1);
        customers.add(c2);
    }
    static void createAccount() {
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String first = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String last = scanner.nextLine();

        Customer newCustomer = new Customer(id, first, last);

        System.out.print("Account Number: ");
        String accNum = scanner.nextLine();
        System.out.print("Starting Balance: ");
        double balance = Double.parseDouble(scanner.nextLine());
        System.out.print("Type (S=Savings / C=Checking): ");
        String type = scanner.nextLine();

        if (type.equalsIgnoreCase("S")) {
            newCustomer.getAccounts().add(new SavingsAccount(accNum, newCustomer, balance, 0.05));
        } else {
            newCustomer.getAccounts().add(new CheckingAccount(accNum, newCustomer, balance, 200.0));
        }

        customers.add(newCustomer);
        System.out.println("✅ Account created!");
    }
    static Account findAccount(String accountNumber) {
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accountNumber)) {
                    return a;
                }
            }
        }
        return null;
    }
    static void viewAccounts() {
        System.out.println("    ALL ACCOUNTS ");
        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                System.out.println("Account #: " + a.getAccountNumber());
                System.out.println("Holder: " + c.getFirstName() + " " + c.getLastName());
                System.out.println("Balance: $" + a.getBalance());
                System.out.println("Type: " + a.getClass().getSimpleName());
            }
        }
    }
    static void deposit() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        Account account = findAccount(accNum);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        try {
            System.out.print("Enter amount to deposit: $");
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be greater than $0.");
                return;
            }
            account.deposit(amount);
            account.printReceipt();
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number.");
        }
    }
    static void withdraw() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.nextLine();
        Account account = findAccount(accNum);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        try {
            System.out.print("Enter amount to withdraw: $");
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be greater than $0.");
                return;
            }
            account.withdraw(amount);
            account.printReceipt();
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number.");
        }
    }
    static void transfer() {
        System.out.print("Enter Source Account Number: ");
        String sourceNum = scanner.nextLine();
        Account source = findAccount(sourceNum);

        if (source == null) {
            System.out.println("Source account not found.");
            return;
        }

        System.out.print("Enter Destination Account Number: ");
        String destNum = scanner.nextLine();
        Account destination = findAccount(destNum);

        if (destination == null) {
            System.out.println("Destination account not found.");
            return;
        }

        try {
            System.out.print("Enter amount to transfer: $");
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be greater than $0.");
                return;
            }
            double balanceBefore = source.getBalance();
            source.withdraw(amount);
            if (source.getBalance() < balanceBefore) {
                destination.deposit(amount);
                System.out.println("Transfer complete!");
                source.printReceipt();
                destination.printReceipt();
            } else {
                System.out.println("Transfer failed. Check source account rules.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number.");
        }
    }
    static void closeAccount() {
        System.out.print("Enter Account Number to close: ");
        String accNum = scanner.nextLine();

        for (Customer c : customers) {
            for (Account a : c.getAccounts()) {
                if (a.getAccountNumber().equals(accNum)) {
                    c.getAccounts().remove(a);
                    System.out.println("Account " + accNum + " closed successfully.");
                    return;
                }
            }
        }
        System.out.println("Account not found.");
    }
}