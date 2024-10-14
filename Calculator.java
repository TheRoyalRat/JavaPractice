import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        requestInput();
    }

    // Get input equation from user
    private static void requestInput() {
        String equation;
        boolean valid = false;
        
        try (Scanner InputScanner = new Scanner(System.in)) {
            while (!valid) {
                System.out.println("Input equation with spaces between entries. (Example: 3 + 5):");
                equation = InputScanner.nextLine();
                
                if (equation == null || equation.isEmpty()) {
                    System.out.println("Error: Please provide input.");
                } else {
                    valid = validateInput(equation);
                }
            }
        }
    }

    // Check input for three vital parts
    private static boolean validateInput(String equation) {
        ArrayList<String> operators = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        String[] equationParts = equation.split(" ");

        if (equationParts.length < 3) {
            System.out.println("Error: Invalid equation.");
            return false;
        }

        for (int index = 0; index < equationParts.length; index++) {
            if (index % 2 == 0) {
                try {
                    values.add(Integer.valueOf(equationParts[index]));
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid value found in equation.");
                    return false;
                }
            } else {
                String operator = equationParts[index];
                // Double backslash needed. One escapes the - in Regex. The other escapes the \ in the Java String.
                if (operator.matches("[+\\-*/%]")) {
                    operators.add(operator);
                } else {
                    System.out.println("Error: Invalid operator found in equation.");
                    return false;
                }
            }
        }

        calculate(operators, values);
        return true;
    }

    // Perform calculations (does not respect order of operators, future improvement?)
    private static void calculate(ArrayList<String> operators, ArrayList<Integer> values) {
        // Calc begins with first value entry.
        Integer result = values.get(0);

        for (int index = 1; index < values.size(); index++) {
            String operator = operators.get(index - 1);
            Integer value = values.get(index);

            switch (operator) {
                case "+" -> result = result + value;
                case "-" -> result = result - value;
                case "*" -> result = result * value;
                case "%" -> result = result % value;
                case "/" -> {
                    if (value == 0) {
                        System.out.println("Error: Division by zero.");
                        return;
                    }
                    result = result / value;
                }
                default -> {
                    System.out.println("Error: Invalid operator discovered.");
                    return;
                }
            }
        }
        System.out.println("Result: " + result);
    }
}
