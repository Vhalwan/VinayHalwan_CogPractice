import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerID;
    private String firstName;
    private String lastName;
    private List<Account> accounts;

    public Customer(String customerID, String firstName, String lastName) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public String getCustomerID() { 
        return customerID; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public String getLastName() { 
        return lastName; 
    }

    public List<Account> getAccounts() { 
        return accounts; 
    }
}
