package Model;

import java.util.ArrayList;

public class Customer {


    //Instance Variables
    private String firstName;
    private String lastName;
    private String ssn;

    //Array for object Customers
    public static ArrayList<Customer> customers = new ArrayList<Customer>();

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    //Constructor Customer
    public Customer(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    @Override
    public String toString(){
        return "\nCustomer Information\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName +  "\n" +
                "SSN: " + ssn +  "\n";
    }

    public String basicInfo(){
        return " Account Number: " + " - Name: " + firstName + " " + lastName;
    }
}

