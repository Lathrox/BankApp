package Model;

import java.util.ArrayList;

public class Account{
    //Array for storing Customers account
    public static ArrayList<Account> accounts = new ArrayList<Account>();
    //Instance Variables
    String owner;
    private double balance = 0;

    //Constructor Model.Account takes object Customer for to see who is the owner of the account.
    public Account(String owner, double balance, int accountNumber){
        this.owner = owner;
        this.balance = balance;
        this.accountNumber = numberOfAccounts;
    }


    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    private int accountNumber;
    private static int numberOfAccounts = 1000000;

    public ArrayList<Account> getAccounts() {
        return accounts;
    }


    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getOwner() {
        return owner;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the accountNumber
     */
    public int getAccountNumber() {
        return accountNumber;
    }
    public void addAccount(Account account) {
        accounts.add(account);
        numberOfAccounts++;
    }
    public void removeAccount(Account account) {
        accounts.remove(account);
    }
    public String accountnumber(){
        return "Account Number: " + this.getAccountNumber();
    }

    public String toString(){
        return "Account Number: " + this.getAccountNumber() + "\n" +
                "Balance: " + this.getBalance() + "\n" ;
    }
}