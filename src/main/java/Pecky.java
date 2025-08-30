import java.util.ArrayList;
import java.util.Scanner;

public class Pecky {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";

    private static String BYE = "Bye. Hope to see you again soon!";

    private static ArrayList<String> LIST = new ArrayList<String>(1);
    private static StringBuilder LIST_STRING = new StringBuilder();

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

        if (s.equals("list")) {
            if (LIST.isEmpty()) {
                printOutput("");
                return 0;
            }
            LIST_STRING = new StringBuilder();
            for (int i=0; i<LIST.size()-1; i++) {
                LIST_STRING.append(i+1);
                LIST_STRING.append(". ");
                LIST_STRING.append(LIST.get(i));
                LIST_STRING.append("\n");
            }

            LIST_STRING.append(LIST.size());
            LIST_STRING.append(". ");
            LIST_STRING.append(LIST.get(LIST.size()-1));

            printOutput(LIST_STRING.toString());

            return 0;
        }

        LIST.add(s);
        printOutput("added: " + s);
        return 0;
    }

    public static void main(String[] args) {
        printOutput(HELLO);
        while (command(takeInput()) == 0);
    }
}
