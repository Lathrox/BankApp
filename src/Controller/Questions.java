package Controller;

import java.util.List;
import java.util.Scanner;

public abstract class Questions {
    public static String askQuestion(String question, List<String> answers) {
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
    public static double getDollarAmount(String question) {
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
    public static double getDeposit() {
        Scanner keyboard = new Scanner(System.in);
        double initialDeposit = 0;
        Boolean valid = false;
        while (!valid) {
            System.out.print("Please enter an initial deposit: ");
            initialDeposit = keyboard.nextDouble();
            if(initialDeposit > 0) {
                valid = true;
            }else{
                System.out.println("You must deposit 0 or more dollars");
            }
        }
        return initialDeposit;
    }
}
