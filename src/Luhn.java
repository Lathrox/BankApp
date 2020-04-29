public class Luhn {

    //Luhn algoritm that returns if the ssn is valid or not.
    public static boolean Luhn(String ssn) {
        if (ssn.length()!=10 ){
            return false;
        }
        int sum = 0;
        boolean alternate = false;
        for (int i = ssn.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ssn.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

}
