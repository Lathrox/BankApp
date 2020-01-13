package Controller;

import Model.Account;
import Model.Customer;
import View.CustomerView;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {


    private Customer model;
    private CustomerView view;

    public CustomerController(Customer model, CustomerView view){
        this.model = model;
        this.view = view;
    }



    public Customer getModel() {
        return model;
    }

    public void setModel(Customer model) {
        this.model = model;
    }

    public CustomerView getView() {
        return view;
    }

    public void setView(CustomerView view) {
        this.view = view;
    }

    public static void createAnCustomer() throws InvalidAccountTypeException {
        //Get account information
        String firstName = Questions.askQuestion("Please enter your first name: ", null);
        String lastName = Questions.askQuestion("Please enter your last name: ", null);
        String ssn = Questions.validateSsn("Please enter your social security number: ", null);

        Customer customer = new Customer(firstName, lastName, ssn);
        customer.addCustomer(customer);
    }
    public static Customer selectCustomer() {
        Customer customer = new Customer(null, null, null);
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Customer> customers = customer.getCustomers();
        if (customers.size() <= 0) {
            System.out.println("No customers at your bank.");
            return null;
        }
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + customers.get(i).basicInfo());
        }
        int i;
        System.out.print("Please enter your selection: ");
        try {
            i = Integer.parseInt(keyboard.nextLine()) - 1;
            customer = customers.get(i);
        } catch (NumberFormatException e) {
            i = -1;
        }
        if (i < 0 || i > customers.size()) {
            System.out.println("Invalid customer selected.");
            i = -1;
        }
        return customer;
    }
    public static String selectCustomerSsn() {
        Customer customer = new Customer(null, null, null);
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Customer> customers = customer.getCustomers();
        if (customers.size() <= 0) {
            System.out.println("No customers at your bank.");
            return null;
        }
        System.out.println("Select an account Customer customer = new Customer(firstName, lastName, ssn);t:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println("\t" + (i + 1) + ") " + customers.get(i).basicInfo());
        }
        int i;
        System.out.print("Please enter your selection: ");
        try {
            i = Integer.parseInt(keyboard.nextLine()) - 1;
        } catch (NumberFormatException e) {
            i = -1;
        }
        if (i < 0 || i > customers.size()) {
            System.out.println("Invalid customer selected.");
            i = -1;
        }
        String customerSsn = customers.get(i).getSsn();
        return customerSsn;
    }
    public static void removeAnCustomer() throws InvalidAccountTypeException {
        Customer customer = new Customer(null, null, null);
        ArrayList<Customer> customers = customer.getCustomers();
        customer = selectCustomer();
        String owner = customer.getSsn();
        AccountController.removeMultipleAccount(owner);
        //check if Customer has accounts in the bank
        //If there is account left withdraw all the cash

        //Run for a double check
        //Will return output no accounts for customer
        customers.remove(customer);

    }
}
