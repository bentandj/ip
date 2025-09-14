package pecky;

import java.util.Scanner;

/**
 * The Ui class is responsible for handling user interactions.
 * It takes in user input and outputs messages to the user.
 */

public class Ui {

    private static MainWindow mainWindow;

    private static final String HELLO = "Hello! I'm Pecky!\n"
            + "What can I do for you?";
    private static final String BYE = "Bye. Hope to see you again soon!";
    private static final String UNKNOWN = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    private static final Scanner scanner = new Scanner(System.in);

    public static void setMainWindow(MainWindow mainWindow) {
        Ui.mainWindow = mainWindow;
    }

    /**
     * Scans for user input.
     *
     * @return A String representing the user input.
     */

    public static String getInput() {
        return scanner.nextLine();
    }

    /**
     * Takes a String, and outputs the result to the user.
     *
     * @param s A String representing the message to be sent.
     */

    public static void print(String s) {
        Ui.mainWindow.printPeckyInput(s);
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
