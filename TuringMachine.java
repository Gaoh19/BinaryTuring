import java.util.Scanner;

public class TuringMachine {
    private static final char ZERO = '0';
    private static final char ONE = '1';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a binary number: ");
        String binaryNumber = scanner.nextLine();

        String result = incrementBinaryNumber(binaryNumber);
        System.out.println("Result: " + result);

        scanner.close();
    }

    private static String incrementBinaryNumber(String binaryNumber) {
        StringBuilder sb = new StringBuilder(binaryNumber);
        int i = sb.length() - 1;
        boolean carry = true;

        while (i >= 0 && carry) {
            if (sb.charAt(i) == ZERO) {
                sb.setCharAt(i, ONE);
                carry = false;
            } else {
                sb.setCharAt(i, ZERO);
                i--;
            }
        }

        if (carry) {
            sb.insert(0, ONE);
        }

        return sb.toString();
    }
}
