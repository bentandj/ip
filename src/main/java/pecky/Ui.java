package pecky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private static final String HELP = initializeHelp();

    public static void setMainWindow(MainWindow mainWindow) {
        assert mainWindow != null;
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



    private static String initializeHelp() {
        // The below was created with the help of google AI.

        InputStream inputStream = Ui.class.getClassLoader().getResourceAsStream("commandList.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Helps the user by printing the command list if an unknown input
     * is encountered 3 times.
     */

    public static void help() {
        if (!HELP.isEmpty()) {
            Ui.print(HELP);
            return;
        }

        Ui.print("You entered 3 incorrect commands."
                    + "\nUnfortunately I lost my command list T_T"
                    + "\nCan't help you :(");
    }
}
