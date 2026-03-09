package atm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {
    private String accountNumber;
    private double balance;
    private String pin;

   
    private List<String> transactions = new ArrayList<>();

    public Client() {}

    public Client(String accountNumber, double balance, String pin) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
    }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    // transactions
    public List<String> getTransactions() { return transactions; }
    public void addTransaction(String t) { transactions.add(t); }
}