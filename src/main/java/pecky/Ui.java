package pecky;

import java.util.Scanner;

/**
 * The Ui class is responsible for handling user interactions.
 * It takes in user input and outputs messages to the user.
 */

public class Ui {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";
    private static String BYE = "Bye. Hope to see you again soon!";
    private static String UNKNOWN = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Scans for user input.
     *
     * @return A String representing the user input.
     */

    public static String getInput() {
        return scanner.nextLine();
    }

    /**
     * Takes a String, applies some consistent formatting and outputs
     * the result to the user.
     *
     * @param s A String representing the message to be sent.
     */

    public static void print(String s) {
        System.out.println("____________________________________________________________");
        System.out.println(s);
        System.out.println("____________________________________________________________");
    }

    /**
     * Says bye to the user.
     */

    public static void bye() {
        Ui.print(BYE);
    }

    /**
     * Says hello to the user.
     */

    public static void hello() {
        Ui.print(HELLO);
    }

    /**
     * Says unknown command to the user.
     */

    public static void unknown() {
        Ui.print(UNKNOWN);
    }
}
