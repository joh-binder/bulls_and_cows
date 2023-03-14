package bullscows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static int bulls;
    private static int cows;
    private static String secretCode;
    private static final Character[] allSymbols = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * Generates a pseudo-random code of a specified length, in which all symbols are unique.
     * @param codeLength the desired length of the code to be generated
     * @return the generated pseudo-random code as a String
     */
    private static String generateSecretCode(int codeLength, int numberOfSymbols) {
        if (numberOfSymbols > 36) {
            String tooManySymbolsMessage = String.format("Error! Cannot generate a secret number with %d symbols. " +
                    "Only up to 36 unique symbols (0-9, a-z) are supported.", numberOfSymbols);
            throw new IllegalArgumentException(tooManySymbolsMessage);
        } else if (codeLength > numberOfSymbols) {
            String notEnoughSymbolsMessage = String.format("Error! Cannot generate a code of length %d " +
                    "using only %d unique symbols.", codeLength, numberOfSymbols);
            throw new IllegalArgumentException(notEnoughSymbolsMessage);
        }

        Random rd = new Random(System.nanoTime());
        StringBuilder sb = new StringBuilder();
        Character[] symbolSelection = Arrays.copyOfRange(allSymbols, 0, numberOfSymbols);
        ArrayList<Character> symbols = new ArrayList<Character>(Arrays.asList(symbolSelection));

        int i = 0;
        while (i < codeLength) {
            // repeatedly picks a random element from symbols, removes it from ArrayList and adds it to StringBuilder
            int randomIndex = rd.nextInt(symbols.size());
            Character symbol = symbols.remove(randomIndex);
            sb.append(symbol);
            i++;
        }
        return sb.toString();
    }

    /** returns as String displaying the symbols potentially used in the code (depending on the parameter numberOfSymbols),
     * e.g. 5 symbols -> returns "0-4"; 20 symbols -> returns "0-9, a-j" **/
    private static String symbolsUsedToString(int numberOfSymbols) {
        if (numberOfSymbols == 1) {
            return "0";
        } else if (numberOfSymbols <= 10) {
            return String.format("0-%c", allSymbols[numberOfSymbols - 1]);
        } else if (numberOfSymbols == 11) {
            return "0-9, a";
        } else if (numberOfSymbols <= 36) {
            return String.format("0-9, a-%c", allSymbols[numberOfSymbols - 1]);
        } else {
            String wrongNumberOfSymbolsMessage = String.format("Error! Cannot print string for %d symbols.", numberOfSymbols);
            throw new IllegalArgumentException(wrongNumberOfSymbolsMessage);
        }
    }

    /**
     * Compares the actual secret number and the number guessed by the user to determine the amount of bulls (= correct
     * digits at correct position) and cows (= correct digits at wrong position).
     * @param userGuess the number guessed by the user, represented as a String
     */
    private static void countBullsAndCows(String userGuess) {
        bulls = 0;
        cows = 0;

        // iterate over every digit in the secret code
        for (int i = 0; i < secretCode.length(); i++) {
            // compare with every digit in the user-guessed code
            for (int j = 0; j < userGuess.length(); j++) {
                if (secretCode.charAt(i) == userGuess.charAt(j)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
    }

    /**
     * Prints the number of bulls and cows corresponding to the user guess.
     */
    private static void printResult() {
        String result;
        if (bulls > 0 && cows > 0) {
            result = bulls + " bull(s) and " + cows + " cow(s)";
        } else if (bulls > 0) {
            result = bulls + " bull(s)";
        } else if (cows > 0) {
            result = cows + " cow(s)";
        } else {
            result = "None";
        }
        System.out.printf("Grade: %s.\n", result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        int codeLength = scanner.nextInt();
        if (codeLength < 1) {
            String codeTooShortMessage = "Error! To play this game, your code needs a length of at least 1.";
            throw new IllegalArgumentException(codeTooShortMessage);
        }
        System.out.println("Input the number of possible symbols in the code:");
        int numberOfSymbols = scanner.nextInt();

        secretCode = generateSecretCode(codeLength, numberOfSymbols);
        System.out.println("The secret is prepared: " + "*".repeat(codeLength) + " (" + symbolsUsedToString(numberOfSymbols) + ")");
        System.out.println("Okay, let's start a game!");

        boolean guessed = false;
        while (!guessed) {
            // prompt user for next number and validate input
            String userGuess = scanner.next();
            for (int i = 0; i < userGuess.length(); i++) {
                if (userGuess.charAt(i) < '0' || userGuess.charAt(i) > '9' && userGuess.charAt(i) < 'a' || userGuess.charAt(i) > 'z') {
                    String invalidCharacterMessage = String.format("Error! Your answer should only contain the symbols "
                            + symbolsUsedToString(numberOfSymbols)+ ", but it contains the character %c.", userGuess.charAt(i));
                    throw new IllegalArgumentException(invalidCharacterMessage);
                    // todo: invalid input should not terminate the game
                }
            }
            if (secretCode.length() != userGuess.length()) {
                String wrongInputLengthMessage = String.format("Error! Wrong input number length. Your guess should " +
                        "have as many digits as the secret number, in this case %d.", secretCode.length());
                throw new IllegalArgumentException(wrongInputLengthMessage);
            }

            countBullsAndCows(userGuess);
            printResult();

            if (bulls == codeLength) {
                System.out.println("Congratulations! You gussed the secret code.");
                guessed = true;
            }
        }

    }
}
