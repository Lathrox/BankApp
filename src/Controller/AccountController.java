package Controller;

import Model.Account;
import View.AccountView;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountController {


    private Account model;
    private AccountView view;

    public AccountController(Account model, AccountView view) {
        this.model = model;
        this.view = view;
    }

disconecct

    public Account getModel() {
        return model;
    }

    public void setModel(Account model) {
        this.model = model;
    }

    public AccountView getView() {
        return view;
    }

    public void setView(AccountView view) {
        this.view = view;
    }

    public void updateView() {
        view.printAccountDetails(model.getOwner(), model.getBalance(), model.getAccountNumber());
    }

    public static int selectAnAccount() throws InvalidAccountTypeException {
        Account account = new Account(null, 0, 0);
        Scanner keyboard = new Scanner(System.in);
        String customerSsn = CustomerController.selectCustomerSsn();
        ArrayList<Account> accounts = account.getAccounts();
        int accountNumber = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (customerSsn.equals(accounts.get(i).getOwner())) {
                System.out.println("\t" + (i + 1) + ") " + accounts.get(i).accountnumber());
            }
        }
        if (accounts.size() == 0) {
            System.out.println("no current account for the customer");
        } else {
            System.out.print("Please enter your selection: ");
            int i;
            try {
                i = Integer.parseInt(keyboard.nextLine()) - 1;
                accountNumber = accounts.get(i).getAccountNumber();
            } catch (NumberFormatException e) {
                i = -1;
                accountNumber = accounts.get(i).getAccountNumber();
            }
            if (i < 0 || i > accounts.size()) {
                System.out.println("Invalid customer selected.");
                i = -1;
            }
        }
        return accountNumber;

    }

    public static void createAnAccount() throws InvalidAccountTypeException {
        String owner = null;
        if (owner == null) {
            owner = CustomerController.selectCustomerSsn();
        }
        Account account = new Account(null, 0, 0);
        double initialDeposit = Questions.getDollarAmount("How much would you like to deposit?: ");

        account.setOwner(owner);
        account.setBalance(initialDeposit);
        account.addAccount(account);
        System.out.println(account.toString());
    }

    public static void makeADeposit() throws InvalidAccountTypeException {
        double amount = 0;
        double balance = 0;
        int accountNumber = selectAnAccount();
        Account account = new Account(null, 0, 0);
        ArrayList<Account> accounts = account.getAccounts();
        int y = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accountNumber == accounts.get(i).getAccountNumber()) {
                account = accounts.get(i);
                balance = accounts.get(i).getBalance();
                y = i;
                amount = Questions.getDollarAmount("How much would you like to deposit?: ");
            }
        }
        if (amount <= 0) {
            System.out.println(accountNumber);
            System.out.println(accounts.get(y).getAccountNumber());
            System.out.println("You cannot deposit negative money.");
            return;
        }
        balance += amount;
        System.out.println("You have deposited $" + amount + " dollars " + "%");
        System.out.println("You now have a balance of $" + balance);
        account.setBalance(balance);
    }

    public static void makeAWithdraw() throws InvalidAccountTypeException {
        double amount = 0;
        double balance = 0;
        int accountNumber = selectAnAccount();
        Account account = new Account(null, 0, 0);
        ArrayList<Account> accounts = account.getAccounts();
        int y = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accountNumber == accounts.get(i).getAccountNumber()) {
                y = i;
                account = accounts.get(i);
                balance = accounts.get(i).getBalance();
                System.out.println("Current balance: " + balance);
                amount = Questions.getDollarAmount("How much would you like to withdraw?: ");
                //amount = Questions.getDeposit();
            }
        }
        if (amount <= 0) {
            System.out.println(accountNumber);
            System.out.println(accounts.get(y).getAccountNumber());
            System.out.println("You cannot deposit negative money.");
            return;
        }
        if (amount + 5 > accounts.get(y).getBalance()) {
            System.out.println("You have insufficient funds.");
            return;
        }
        balance -= amount + 5;
        System.out.println("You have withdrawn $" + amount + " dollars and incurred a fee of $5");
        System.out.println("You now have a balance of $" + balance);
        account.setBalance(balance);
    }
    public static void listBalance() throws InvalidAccountTypeException {
        double amount = 0;
        double balance = 0;
        int accountNumber = selectAnAccount();
        Account account = new Account(null, 0, 0);
        ArrayList<Account> accounts = account.getAccounts();
        int y = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (accountNumber == accounts.get(i).getAccountNumber()) {
                System.out.println(accounts.get(i).toString());
            }
        }
    }
    public static void removeAccount() throws InvalidAccountTypeException {
        double balance = 0;
        double amount = 0;
        int y = 0;
        Account account = new Account(null, 0, 0);
        ArrayList<Account> accounts = account.getAccounts();
        int accountNumber = selectAnAccount();
        for (int i = 0; i < accounts.size(); i++) {
            if (accountNumber == accounts.get(i).getAccountNumber()) {
                y = i;
                account = accounts.get(i);
                balance = accounts.get(i).getBalance();
                System.out.println("Current balance: " + balance);
                amount = balance;
            }
            balance -= amount; // = 0
            account.setBalance(balance);
            System.out.println("You have withdraw " + amount + "new balance is " + accounts.get(y).getBalance());
            System.out.println("We will now delete your account");
            //remove account
            accounts.get(y).removeAccount(account);
        }
    }
    public static void removeMultipleAccount(String owner) throws InvalidAccountTypeException {
        double balance = 0;
        double amount = 0;
        double totalBalance = 0;
        Account account = new Account(null, 0, 0);
        ArrayList<Account> accounts = account.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (owner == accounts.get(i).getOwner()) {
                account = accounts.get(i);
                balance = accounts.get(i).getBalance();
                System.out.println("Current balance: " + balance);
                amount = balance;

                balance -= amount; // = 0
                account.setBalance(balance);
                totalBalance = totalBalance + amount;
                //remove account
                accounts.get(i).removeAccount(account);
                //When account is removed the arraylist will shrink 1 value
                // go back one step in the arraylist to check all the accounts
                i--;
            }
        }
        System.out.println("We have deleted all your accounts");
        System.out.println("You had a total amount of $ " + totalBalance);
    }

    /**
     * @throws InvalidAccountTypeException
     */
    public  static void makeATransfer() throws InvalidAccountTypeException {
        boolean accountsSelected = false;
        double amount, balanceW = 0, balanceD = 0;
        int w = 0;
        int d = 0;
        //1. select account to withdraw from
        Account withdrawAccount = new Account(null, 0, 0);
        Account depositAccount = new Account(null, 0, 0);
        //Only fetching one
        ArrayList<Account> accounts = withdrawAccount.getAccounts();
        int withDrawAccountNumber = selectAnAccount();
        //Withraw account
        do {
            for (int i = 0; i < accounts.size(); i++) {
                //Todo withraw all the  cash from the account
                if (withDrawAccountNumber == accounts.get(i).getAccountNumber()) {
                    w = i;
                    withdrawAccount = accounts.get(i);
                    balanceW = accounts.get(i).getBalance();
                }
            }
            //deposit account
            int DepositDrawAccountNumber = selectAnAccount();
            for (int y = 0; y < accounts.size(); y++) {
                //Todo withraw all the  cash from the account
                if (DepositDrawAccountNumber == accounts.get(y).getAccountNumber()) {
                    d = y;
                    depositAccount = accounts.get(y);
                    balanceD = accounts.get(y).getBalance();
                }
            }
            //Checks that there is two accounts selected
            if ((withdrawAccount.getAccountNumber() | depositAccount.getAccountNumber()) != 0){
                accountsSelected = true;
            }
        }while(!accountsSelected);
        //2.
        amount = Questions.getDollarAmount("How much would you like to transfer?: ");
        if (amount <= 0) {
            System.out.println("You cannot deposit negative money.");
            return;
        }
        if (amount + 5 > balanceW) {
            System.out.println("You have insufficient funds.");
            return;
        }
        balanceW = balanceW - amount - 5;
        balanceD = balanceD + amount;
       withdrawAccount.setBalance(balanceW);
       depositAccount.setBalance(balanceD);
        System.out.println("Account: " + withdrawAccount.accountnumber());
        System.out.println("has transferred " + amount + " to account: " + depositAccount.accountnumber());
        System.out.println("with a transfer fee of $5");
        System.out.println("You now have a balance of $" + withdrawAccount.getBalance());
    }
}
