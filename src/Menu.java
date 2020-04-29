
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    //Instance Variables
    Bank bank = new Bank();
    //printes menu and asks for menu choice
    public void runMenu() {
        boolean exit = false;
        printHeader();
        while (!exit) {
            printMenu();
            int choice = getMenuChoice();
            performAction(choice);
        }
    }
    private void printHeader() {
        System.out.println("+-----------------------------------+");
        System.out.println("|      Welcome to Sweetbank         |");
        System.out.println("|        Awesome Bank App           |");
        System.out.println("+-----------------------------------+");
    }
    private void printMenu() {
        displayHeader("Please make a selection");
        System.out.println("1) Create a new Customer");
        System.out.println("2) Create a new Account");
        System.out.println("3) Make a deposit");
        System.out.println("4) Make a withdrawal");
        System.out.println("5) List account balance");
        System.out.println("6) Make a transfer");
        System.out.println("7) remove an account");
        System.out.println("8) remove an customer");
        System.out.println("0) Exit");
    }
    //Controller that menu choice is valid
    private int getMenuChoice() {
        Scanner keyboard = new Scanner(System.in);
        int choice = -1;
        do {
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection. Numbers only please.");
            }
            if (choice < 0 || choice > 8) {
                System.out.println("Choice outside of range. Please chose again.");
            }
        } while (choice < 0 || choice > 8);
        return choice;
    }
    private void performAction(int choice)  {
        switch (choice) {
            case 0:
                System.out.println("Thank you for using our application.");
                System.exit(0);
                break;
            case 1: {
                createAnCustomer();
                break;
            }
            case 2:{
                createAnAccount();
                break;
            }
            case 3:
                makeADeposit();
                break;
            case 4:
                makeAWithdrawal();
                break;
            case 5:
                listBalances();
                break;
            case 6:
                makeATransfer();
                break;
            case 7:
                removeAccount();
                break;
            case 8:
                removeCustomerAndAccount();
                break;
            default:
                System.out.println("Unknown error has occurred.");
                System.exit(0);
        }
    }
    //Making asking for questions more efficient
    private String askQuestion(String question, List<String> answers) {
        String response = "";
        Scanner keyboard = new Scanner(System.in);
        boolean choices = ((answers == null) || answers.size() == 0) ? false : true;
        boolean firstRun = true;
        do {
            if (!firstRun) {
                System.out.println("Invalid selection. Please try again.");
            }
            System.out.print(question);
            if (choices) {
                System.out.print("(");
                for (int i = 0; i < answers.size() - 1; ++i) {
                    System.out.print(answers.get(i) + "/");
                }
                System.out.print(answers.get(answers.size() - 1));
                System.out.print("): ");
            }
            //converts respons to lowercase correctly
                response = keyboard.nextLine().toLowerCase();
            firstRun = false;
            if (choices) {
                if (!answers.contains(response)){
                    response = "";
                }
            }
            //checks that answear only contain characters.
            if((!response.matches("^[a-zA-Z]*$"))){
                response = "";
            }
        } while (response.length() == 0);
        return response;
    }
    public static String validateSsn(String question, Object o) {
        Scanner keyboard = new Scanner(System.in);
        String ssn = "";
        boolean firstRun = true;
        //Loop that runs until ssn matches every requirement
        do {
            if (!firstRun) {
                System.out.println("Invalid selection. Please try again.");
            }
            System.out.print(question);

        boolean valid = false;
        while (!valid) {
            ssn = keyboard.nextLine();
            //Controlls that ssn only contain numbers
            if (!ssn.matches("^[0-9]*$")){
                ssn="";
            }
            //Checks that ssn is valid
            if (Luhn.Luhn(ssn)) {
                valid = true;
            } else {
                System.out.println("You must enter a valid ssn.");
            }
        }
        } while (ssn.length() == 0);
        return ssn;
    }
    private void createAnCustomer() {
        displayHeader("Create an Customer");
        //Get account information
        String firstName = askQuestion("Please enter your first name: ", null);
        String lastName = askQuestion("Please enter your last name: ", null);
        String ssn = validateSsn("Please enter your social security number: ", null);

        Customer customer = new Customer(firstName, lastName, ssn);
        bank.addCustomer(customer);
    }
    private void createAnAccount() {
        if (bank.getCustomers().size()<=0){
            System.out.println("no customers at the bank");
        }else {
            displayHeader("Create an Account");
            int customer = selectCustomer();
            String ssn = bank.getCustomers().get(customer).getSsn();
            //Get account information
            double initialDeposit = getDollarAmount("How much would you like to deposit?: ");
            if (initialDeposit <= 0) {
                System.out.println("You cannot deposit negative money.");
            }
            Account account = new Account(ssn, initialDeposit, 0);
            bank.customers.get(customer).addAccount(account);
        }
    }
    private double getDollarAmount(String question) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print(question);
        double amount = 0;
        try {
            amount = Double.parseDouble(keyboard.nextLine());
        } catch (NumberFormatException e) {
            amount = 0;
        }
        return amount;
    }
    private void makeADeposit() {
        displayHeader("Make a Deposit");
        int customer = selectCustomer();
        if (customer == -1) {
            return;
        }
        int account = selectAccount(customer);
        if (account == -1){
            return;
        }
        if (account >= 0) {
            double amount = getDollarAmount("How much would you like to deposit?: ");
            if (amount <= 0) {
                System.out.println("You cannot deposit negative money.");
            }
            bank.customers.get(customer).accounts.get(account).deposit(amount);
        }
    }
    private void makeAWithdrawal() {
        displayHeader("Make a Withdrawal");
        int customer = selectCustomer();
        if (customer == -1) {
            return;
        }
        int account = selectAccount(customer);
        if (account == -1){
            return;
        }
        double amount = getDollarAmount("How much would you like to withdraw?: ");
            bank.customers.get(customer).accounts.get(account).withdraw(amount);
    }
    private void makeATransfer() {
        double amount = 0, balanceW = 0, balanceD = 0;
        int withdrawCustomer = -1,withdrawAccount = -1, depositCustomer = -1, depositAccount = -1;
        int w = 0;
        int d = 0;
        //1. select account to withdraw from
        if(bank.getCustomers().isEmpty()) {
            System.out.println("No customers at the bank");
            return;
        }
        withdrawCustomer = selectCustomer();
        withdrawAccount = selectAccount(withdrawCustomer);
        //2. select account to deposit to
        depositCustomer = selectCustomer();
        depositAccount = selectAccount(depositCustomer);
        //3. ask how much to transfer
        if (withdrawAccount != 0 || depositAccount != 0) {
            System.out.println("controll that both customers has a bank account");
            return;
        }
        //Check that they don't transfer money to the same account.
        if (bank.customers.get(withdrawCustomer).accounts.get(withdrawAccount).equals(bank.customers.get(depositCustomer).accounts.get(depositAccount))){
            System.out.println("You can't transfer money to the same account");
            return;
        }
        amount = getDollarAmount("How much would you like to transfer?: ");
        //controll that you can withdraw the amount from the account
        if (amount <= 0) {
            System.out.println("You cannot deposit negative money.");
            return;
        } else if (amount + 5> bank.customers.get(withdrawCustomer).accounts.get(withdrawAccount).getBalance()) {
            System.out.println("You have insufficient funds.");
            return;
    } else {
            bank.customers.get(withdrawCustomer).accounts.get(withdrawAccount).withdraw(amount);
            bank.customers.get(depositCustomer).accounts.get(depositAccount).deposit(amount);

        }
    }
    private void listBalances() {
        displayHeader("List Account Details");
        int customer = selectCustomer();
        if (customer == -1) {
            return;
        }
        int account = selectAccount(customer);
        if (account == -1){
            return;
        }
        displayHeader("Account Details");
        System.out.println(bank.customers.get(customer).accounts.get(account).basicInfo());
    }
    private void displayHeader(String message){
        System.out.println();
        int width = message.length() + 6;
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for(int i = 0; i < width; ++i){
            sb.append("-");
        }
        sb.append("+");
        System.out.println(sb.toString());
        System.out.println("|   " + message + "   |");
        System.out.println(sb.toString());
    }
    private int selectCustomer() {
        Scanner keyboard = new Scanner(System.in);
        boolean firstRun = true;
        int customer;
        ArrayList<Customer> customers = bank.getCustomers();
        if (customers.size() <= 0) {
            System.out.println("No customers at your bank.");
            return -1;
        }
        System.out.println("Select an customer:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + customers.get(i).basicInfo());
        }

        System.out.print("Please enter your selection: ");
        do {
            if (!firstRun) {
                System.out.println("Invalid selection. Please try again.");
            }
            customer = keyboard.nextInt() - 1;
            firstRun = false;
            }while (customer < 0 || customer >= customers.size());
        return customer;
    }
    private int selectAccount(int customer) {
        Scanner keyboard = new Scanner(System.in);
        int account;
        boolean firstRun =true;
        if (bank.customers.get(customer).accounts.size() <= 0) {
            System.out.println("No accounts for the customer.");
            return -1;
        }
        System.out.println("Select an account:");
        for (int i = 0; i < bank.customers.get(customer).accounts.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + bank.customers.get(customer).accounts.get(i).basicInfo());
        }
        System.out.print("Please enter your selection: ");
        do {
            if (!firstRun) {
                System.out.println("Invalid selection. Please try again.");
            }
            account = keyboard.nextInt() - 1;
            firstRun = false;
        }while (account < 0 || account >= bank.customers.get(customer).accounts.size());
        return account;
    }
    private void removeCustomerAndAccount() {
        double totalBalance = 0;
        int customer = selectCustomer();
        if (customer == -1){
            return;
        }
        //Controll that user want to go trough with removing everything
        String accountType = askQuestion("Are you sure you want to delete all" +
                "your accounts and stop being our customer", Arrays.asList("yes", "no"));
        //Remove all accounts
        for (int i = 0; i < bank.customers.get(customer).accounts.size(); i++) {
            double balance = 0;
            balance = bank.customers.get(customer).accounts.get(i).getBalance();
            System.out.println("Current balance: " + balance);
            //Calculate how much in total that has been withdrawed
            totalBalance = totalBalance + balance;
            //done this way to simulate that the currency has been withdrawed.
            //withdraw all the money from the account
            balance -= balance; // = 0
            bank.customers.get(customer).accounts.get(i).setBalance(balance);
            //remove account
            Account removeAccount = bank.customers.get(customer).accounts.get(i);
            bank.customers.get(customer).accounts.remove(removeAccount);
            //When account is removed the arraylist will shrink 1 value
            // go back one step in the arraylist to check all the accounts
            i--;
        }
        System.out.println("We have deleted all your accounts");
        System.out.println("You had a total amount of $ " + totalBalance);

        Customer removeCustomer = bank.customers.get(customer);
        bank.customers.remove(removeCustomer);
    }
    private void removeAccount() {
        double balance = 0;
        double amount = 0;
        int customer = selectCustomer();
        if (customer == -1) {
            return;
        }
        int account = selectAccount(customer);
        if (account == -1){
            return;
        }
        balance = bank.customers.get(customer).accounts.get(account).getBalance();
        balance -= amount; // = 0
        bank.customers.get(customer).accounts.get(account).setBalance(balance);
        System.out.println("You have withdraw " + amount + "new balance is " + balance);
        System.out.println("We will now delete your account");
        //remove account
        Account removeAccount = bank.customers.get(customer).accounts.get(account);
        bank.customers.get(customer).accounts.remove(removeAccount);
    }
}