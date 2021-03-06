import java.util.Objects;

public class Account {
    String ssn;
    private double balance = 0;
    private int accountNumber;
    //static int that increments for each account that is created.
    //increament is handled in the constructur.
    private static int numberOfAccounts = 1000000;

    public Account(String ssn, double balance, int accountNumber){
        this.ssn=ssn;
        this.balance=balance;
        this.accountNumber=numberOfAccounts++;
    }
    Account(){
        accountNumber = numberOfAccounts++;
    }


    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
    
    public void withdraw(double amount){
        amount = amount + 5;
        if(amount <= 0){
            System.out.println("You cannot withdraw negative money.");
            return;
        }
        if (balance < amount) {
            System.out.println("You have insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.println("You have withdrawn $" + amount + " dollars and incurred a fee of $5");
        System.out.println("You now have a balance of $" + balance);
    }
    
    public void deposit(double amount){
        if(amount <= 0){
            System.out.println("You cannot deposit negative money.");
            return;
        }
        balance += amount;
        System.out.println("You have deposited $" + amount);
        System.out.println("You now have a balance of $" + balance);
    }
    public void removeAccount(Account account) {
        account.removeAccount(account);
    }
    public String basicInfo(){
        return " Account Number: " + accountNumber + " - Balance: " + " " + balance;
    }

}
