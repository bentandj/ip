import java.util.Scanner;

public class Pecky {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";

    private static String BYE = "Bye. Hope to see you again soon!";

    private static void printOutput(String s) {
        System.out.println("____________________________________________________________");
        System.out.println(s);
        System.out.println("____________________________________________________________");
    }

    private static String takeInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static int command(String s) {
        if (s.equals("bye")) {
            printOutput(BYE);
            return 1;
        }

        printOutput(s);
        return 0;
    }

    public static void main(String[] args) {
        printOutput(HELLO);
        while (command(takeInput()) == 0);
    }
}
