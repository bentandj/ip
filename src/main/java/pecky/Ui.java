package pecky;

import java.util.Scanner;

public class Ui {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";
    private static String BYE = "Bye. Hope to see you again soon!";
    private static String UNKNOWN = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    private static Scanner scanner = new Scanner(System.in);

    public static String getInput() {
        return scanner.nextLine();
    }

    public static void print(String s) {
        System.out.println("____________________________________________________________");
        System.out.println(s);
        System.out.println("____________________________________________________________");
    }

    public static void bye() {
        Ui.print(BYE);
    }

    public static void hello() {
        Ui.print(HELLO);
    }

    public static void unknown() {
        Ui.print(UNKNOWN);
    }
}
