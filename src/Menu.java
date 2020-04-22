
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    //Instance Variables
    Bank bank = new Bank();

    boolean exit;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.runMenu();
    }

    public void runMenu() {
        printHeader();
        while (!exit) {
            printMenu();
            int choice = getMenuChoice();
            performAction(choice);
        }
    }

    private void printHeader() {
        System.out.println("+-----------------------------------+");
        System.out.println("|        Welcome to Mr.V's          |");
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

    private void performAction(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Thank you for using our application.");
                System.exit(0);
                break;
            case 1: {
                try {
                    createAnCustomer();
                } catch (InvalidAccountTypeException ex) {
                    System.out.println("Customer was not created successfully.");
                }
                break;
            }
            case 2:{
                try {
                    createAnAccount();
                } catch (InvalidAccountTypeException ex) {
                    System.out.println("Customer was not created successfully.");
                }
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
                try {
                    removeAccount();
                } catch (InvalidAccountTypeException e) {
                    System.out.println("Account was not removed successfully.");
                }
                break;
            case 8:
                try {
                    removeCustomer();
                } catch (InvalidAccountTypeException e) {
                    System.out.println("Customer was not removed successfully.");
                }
                break;
            default:
                System.out.println("Unknown error has occured.");
        }
    }



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
            response = keyboard.nextLine();
            firstRun = false;
            if (!choices) {
                break;
            }
        } while (!answers.contains(response));
        return response;
    }
    public static String validateSsn(String question, Object o) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print(question);
        String ssn = "";
        boolean valid = false;
        while (!valid) {
            ssn = keyboard.nextLine();
            if (Luhn.Luhn(ssn)) {
                valid = true;
            } else {
                System.out.println("You must enter a valid ssn.");
            }
        }
        return ssn;
    }

    private double getDeposit(String accountType) {
        Scanner keyboard = new Scanner(System.in);
        double initialDeposit = 0;
        Boolean valid = false;
        while (!valid) {
            System.out.print("Please enter an initial deposit: ");
            try {
                initialDeposit = Double.parseDouble(keyboard.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Deposit must be a number.");
            }
            if (accountType.equalsIgnoreCase("checking")) {
                if (initialDeposit < 100) {
                    System.out.println("Checking accounts require a minimum of $100 dollars to open.");
                } else {
                    valid = true;
                }
            } else if (accountType.equalsIgnoreCase("savings")) {
                if (initialDeposit < 50) {
                    System.out.println("Savings accounts require a minimum of $50 dollars to open.");
                } else {
                    valid = true;
                }
            }
        }
        return initialDeposit;
    }

    private void createAnCustomer() throws InvalidAccountTypeException {
        displayHeader("Create an Customer");
        //Get account information
        String firstName = askQuestion("Please enter your first name: ", null);
        String lastName = askQuestion("Please enter your last name: ", null);
        String ssn = validateSsn("Please enter your social security number: ", null);

        Customer customer = new Customer(firstName, lastName, ssn);
        bank.addCustomer(customer);
        //We can create an account now;
    }
    private void createAnAccount() throws InvalidAccountTypeException {
        displayHeader("Create an Account");
        int customer = selectCustomer();
        String ssn = bank.getCustomers().get(customer).getSsn();
        //Get account information
        double initialDeposit = getDollarAmount("How much would you like to deposit?: ");

        Account account = new Account(ssn, initialDeposit,0);
        bank.customers.get(customer).addAccount(account);
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
        int account = selectAccount(customer);
        if (account >= 0) {
            double amount = getDollarAmount("How much would you like to deposit?: ");
            if(amount <= 0){
                System.out.println("You cannot deposit negative money.");
            }
            bank.customers.get(customer).accounts.get(account).deposit(amount);
        }
    }

    private void makeAWithdrawal() {
        displayHeader("Make a Withdrawal");
        int customer = selectCustomer();
        int account = selectAccount(customer);
        if (account >= 0) {
            double amount = getDollarAmount("How much would you like to withdraw?: ");
            if(amount + 5 > bank.customers.get(customer).accounts.get(account).getBalance()) {
                System.out.println("You have insufficient funds.");
                return;
            }else {
                bank.customers.get(customer).accounts.get(account).withdraw(amount);
            }
        }
    }

    private void makeATransfer() {
        double amount = 0, balanceW = 0, balanceD = 0;
        int w = 0;
        int d = 0;
        //1. select account to withdraw from
        int withdrawCustomer = selectCustomer();
        int withdrawAccount = selectAccount(withdrawCustomer);
        //2. select account to deposit to
        int depositCustomer = selectCustomer();
        int depositAccount = selectAccount(depositCustomer);
        //3. ask how much to transfer
        if (withdrawAccount >= 0) {
            amount = getDollarAmount("How much would you like to transfer?: ");
        }
        //controll that you can withdraw the amount from the account
        if(amount <= 0){
            System.out.println("You cannot deposit negative money.");
            return;
        }else if(amount + 5 > bank.customers.get(withdrawCustomer).accounts.get(withdrawAccount).getBalance()){
            System.out.println("You have insufficient funds.");
            return;
        }else {
            bank.customers.get(withdrawCustomer).accounts.get(withdrawAccount).withdraw(amount);
            bank.customers.get(depositCustomer).accounts.get(depositAccount).deposit(amount);
            System.out.println("money is transfered");
        }

    }
    private void listBalances() {
        displayHeader("List Account Details");
        int customer = selectCustomer();
        int account = selectAccount(customer);
        if (account >= 0) {
            displayHeader("Account Details");
            System.out.println(bank.customers.get(customer).accounts.get(account).basicInfo());

        }
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
        ArrayList<Customer> customers = bank.getCustomers();
        if (customers.size() <= 0) {
            System.out.println("No customers at your bank.");
            return -1;
        }
        System.out.println("Select an customer:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + customers.get(i).basicInfo());
        }
        int customer;
        System.out.print("Please enter your selection: ");
        try {
            customer = Integer.parseInt(keyboard.nextLine()) - 1;
        } catch (NumberFormatException e) {
            customer = -1;
        }
        if (customer < 0 || customer > customers.size()) {
            System.out.println("Invalid account selected.");
            customer = -1;
        }
        return customer;
    }

    private int selectAccount(int customer) {
        Scanner keyboard = new Scanner(System.in);
        int account;
        if (bank.customers.get(customer).accounts.size() <= 0) {
            System.out.println("No accounts for the customer.");
            return -1;
        }
        System.out.println("Select an account:");
        for (int i = 0; i < bank.customers.get(customer).accounts.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + bank.customers.get(customer).accounts.get(i).basicInfo());
        }
        System.out.print("Please enter your selection: ");
        try {
            account = keyboard.nextInt() - 1;
        } catch (NumberFormatException e) {
            account = -1;
        }
        if (account < 0 || account > bank.customers.get(customer).accounts.size()) {
            System.out.println("Invalid account selected.");
            account = -1;
        }
        return account;
    }
    private void removeAccount() throws InvalidAccountTypeException {
        double balance = 0;
        double amount = 0;
        int customer = selectCustomer();
        int account = selectAccount(customer);
        balance = bank.customers.get(customer).accounts.get(account).getBalance();
        balance -= amount; // = 0
        bank.customers.get(customer).accounts.get(account).setBalance(balance);
        System.out.println("You have withdraw " + amount + "new balance is " + balance);
        System.out.println("We will now delete your account");
        //remove account
        Account removeAccount = bank.customers.get(customer).accounts.get(account);
        bank.customers.get(customer).accounts.remove(removeAccount);
    }
    private void removeCustomer() throws InvalidAccountTypeException {
        int customer = selectCustomer();
        if (bank.customers.get(customer).accounts.size()>0){
            System.out.println("remove all the accounts before you can remove the customer");
            return;
        }else {
            Customer removeCustomer = bank.customers.get(customer);
            bank.customers.remove(removeCustomer);
        }
    }
}
