/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;


public class Customer {
    private final String firstName;
    private final String lastName;

    private final String ssn;
    ArrayList<Account> accounts = new ArrayList<Account>();


    Customer(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }
    void addAccount(Account account) {
        accounts.add(account);
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
        return   "Name: " + firstName + " " + lastName;
    }

    
}
