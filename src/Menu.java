import Controller.AccountController;
import Controller.CustomerController;
import Controller.InvalidAccountTypeException;
import Model.Account;
import Model.Customer;
import View.AccountView;
import View.CustomerView;

import java.util.Scanner;

//Package imports

public class Menu {
    boolean exit;

    public static void main(String[] args) throws InvalidAccountTypeException {



        Customer customer = new Customer(null, null, null);
        Account account = new Account(null, 0, 0);

        //Create a view : to write student details on console
        CustomerView customerView = new CustomerView();
        AccountView accountView = new AccountView();

        //AccountController accountController = new AccountController(account, accountView);
        //CustomerController customerController = new CustomerController(customer, customerView);


        Menu menu = new Menu();
        menu.runMenu();
    }

        public void runMenu() throws InvalidAccountTypeException {
            printHeader();
            while (!exit) {
                printMenu();
                int choice = getMenuChoice();
                performAction(choice);
            }
        }

        private void printHeader() {
            System.out.println("+-----------------------------------+");
            System.out.println("|       Welcome to Sweetbank        |");
            System.out.println("|            Bank App               |");
            System.out.println("+-----------------------------------+");
        }

        private void printMenu() {
            displayHeader("Please make a selection");
            System.out.println("1) Create a new Customer");
            System.out.println("2) Make a new account");
            System.out.println("3) List account balance");
            System.out.println("4) Make a deposit");
            System.out.println("5) Make a withdrawal");
            System.out.println("6) Make a transfer");
            System.out.println("7) Remove account");
            System.out.println("8) Remove customer");
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

        private void performAction(int choice) throws InvalidAccountTypeException {
            switch (choice) {
                case 0:
                    System.out.println("Thank you for using our application.");
                    System.exit(0);
                    break;
                case 1: {
                    try {
                        displayHeader("Create an Customer");
                        CustomerController.createAnCustomer();
                    } catch (InvalidAccountTypeException ex) {
                        System.out.println("Customer was not created successfully.");
                    }
                }
                break;
                case 2: {
                    try {

                        displayHeader("Create an Account");
                        AccountController.createAnAccount();
                        break;
                    }catch (InvalidAccountTypeException ex){
                        System.out.println("Account was not created successfully.");
                    }
                }
                case 3: {
                    try {
                        displayHeader("List balance");
                        AccountController.listBalance();
                        break;
                    }catch (InvalidAccountTypeException ex){
                        System.out.println("An error occurred");
                    }
                }
                case 4:
                    AccountController.makeADeposit();
                    break;
                case 5:
                    AccountController.makeAWithdraw();
                    break;
                case 6:
                    AccountController.makeATransfer();
                    break;
                case 7:
                    AccountController.removeAccount();
                    break;
                case 8:
                    CustomerController.removeAnCustomer();
                    break;
                default:
                    System.out.println("Unknown error has occured.");
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
}
