package bullscows;

import java.util.*;

public class Main {
    private static int bulls = 0, cows = 0;
    private static final String secretCode = getRandomCode();

    public static void main(String[] args) {
        printGrade();
    }

    // Returns random index from list using Random class and nanoTime
    private static String getRandomCharacter(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null; // Return null for an empty or null list
        }

        long nanoTime = System.nanoTime();
        Random random = new Random(nanoTime);
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static void printGrade() {
        int turns = 1;
        boolean isCorrect = false;

        while (!isCorrect) {
            System.out.printf("Turn %d:\n", turns);
            String guess = guess();
            compareCodes(guess, secretCode);
            System.out.println(secretCode);

            if (guess.equals(secretCode)) {
                System.out.printf("Grade: %d bulls.\n", bulls);
                System.out.println("Congratulations! You guessed the secret code.");
                isCorrect = true;
            } else if (bulls > 0 && cows > 0) {
                if (bulls > 1 && cows > 1) {
                    System.out.printf("Grade: %d bulls and %d cows.\n", bulls, cows);
                } else if (bulls == 1 && cows > 1 ) {
                    System.out.printf("Grade: %d bull and %d cows.\n", bulls, cows);
                } else if (bulls > 1 && cows == 1) {
                    System.out.printf("Grade: %d bulls and %d cow.\n", bulls, cows);
                } else {
                    System.out.printf("Grade: %d bull and %d cow.\n", bulls, cows);
                }
                turns++;
                cows = 0;
                bulls = 0;
            } else if (bulls == 0 && cows > 0) {
                if (cows > 1) {
                    System.out.printf("Grade: %d cows.\n", cows);
                } else {
                    System.out.printf("Grade: %d cow.\n", cows);
                }
                turns++;
                cows = 0;
                bulls = 0;
            } else if (bulls > 0 && cows == 0) {
                if (bulls > 1) {
                    System.out.printf("Grade: %d bulls.\n", bulls);
                } else {
                    System.out.printf("Grade: %d bull.\n", bulls);
                }
                turns++;
                cows = 0;
                bulls = 0;
            }
            else {
                System.out.println("Grade: none");
                turns++;
            }
        }
    }

    public static void compareCodes(String guess, String secretCode) {
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            } else if (guess.contains(String.valueOf(secretCode.charAt(i)))){
                cows++;
            }
        }
    }

    private static ArrayList<String> password() {
        StringBuilder password = new StringBuilder();

        // Add digits (0-9)
        for (char c = '0'; c <= '9'; c++) {
            password.append(c);
        }

        // Add lowercase letters (a-z)
        for (char c = 'a'; c <= 'z'; c++) {
            password.append(c);
        }

        String[] passwordArray = password.toString().split("");

        return new ArrayList<>(Arrays.asList(passwordArray));
    }

    // Generates a random code from list of strings 1 to 9
    private static String getRandomCode() {
        Scanner scan = new Scanner(System.in);
        StringBuilder randomCode = new StringBuilder();
        String secretCodeString = "";
        String secretLen, symbols;

        System.out.println("Input the length of the secret code:");
        secretLen = scan.nextLine();
        Scanner scanner = new Scanner(secretLen);

        if (!scanner.hasNextInt()) {
            System.out.println(secretLen + " isn't a valid number.");
            System.exit(1);
        }

        System.out.println("Input the number of possible symbols in the code:");
        symbols = scan.nextLine();

        if (Integer.parseInt(symbols) < Integer.parseInt(secretLen)) {
            System.out.printf("Error: it's not possible to generate a code with a length of %s with %s unique symbols.\n", secretLen, symbols);
            System.exit(1);
        }

        String stars = "";
        for (int i = 0; i < Integer.parseInt(secretLen); i++) {
            stars += "*";
        }

        System.out.printf("The secret is prepared: %s \n", stars + " (0-9, a-" + password().get(Integer.parseInt(symbols)-1) +")");

        String character = getRandomCharacter(password().subList(0, Integer.parseInt(symbols)));

        if (Integer.parseInt(secretLen) > 0 && Integer.parseInt(secretLen) < 37) {
            for (int i = 0; i < Integer.parseInt(secretLen); i++) {
                if (!randomCode.toString().contains(character)) {
                    randomCode.append(character);
                    password().remove(character);
                }
                character = getRandomCharacter(password());
            }
            secretCodeString = randomCode.toString();
        } else if (Integer.parseInt(secretLen) > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else {
            System.out.printf("Error: can't generate a secret random number with a length of %d because there aren't enough unique digits.", secretLen);
        }
        System.out.println(secretCodeString);
        return secretCodeString;
    }

    private static String guess() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

}
