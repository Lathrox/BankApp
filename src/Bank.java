
import java.util.ArrayList;


public class Bank {
    ArrayList<Customer> customers = new ArrayList<Customer>();

    
    void addCustomer(Customer customer) {
        //Check if there already is an Customer with the ssn
        for (int i = 0; i<customers.size(); i++){
             if (customers.get(i).getSsn().equals(customer.getSsn())){
                 System.out.println("there is already an customer with that ssn");
                 return;
             }

        }
        customers.add(customer);
    }

    Customer getCustomer(int account) {
        return customers.get(account);
    }
    
    ArrayList<Customer> getCustomers(){
        return customers;
    }
    
}
