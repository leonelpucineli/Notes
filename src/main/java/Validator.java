import java.util.Scanner;

public class Validator {
    private static Scanner keyboard = new Scanner(System.in);

    public static int forInt(int min, int max, String errorMessage){
        int num;

        if (errorMessage == "") {
            errorMessage = "You need to choose a valid number between those especified (" + min + " and " + max + ").\n>> ";
        }

        while (true) {
            try {
                num = keyboard.nextInt();
                if (min <= num && max >= num) {
                    break;
                } else {
                    System.out.print(errorMessage);
                }
            } catch (Exception e) {
                System.out.print(errorMessage);
                keyboard.next();
            }
        }

        return num;
    }
}



