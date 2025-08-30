import java.util.ArrayList;
import java.util.Scanner;

public class Pecky {

    private static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        private void markDone() {
            this.isDone = true;
        }

        private void markNotDone() {
            this.isDone = false;
        }

        public String getStatusIcon() {
            return (isDone ? "X" : " "); // mark done task with X
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + description;
        }
    }

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";

    private static String BYE = "Bye. Hope to see you again soon!";

    private static Task[] LIST = new Task[100];
    private static int LIST_SIZE = 0;
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
            if (LIST_SIZE == 0) {
                printOutput("");
                return 0;
            }

            LIST_STRING = new StringBuilder();
            LIST_STRING.append("Here are the tasks in your list:\n");
            for (int i=0; i<LIST_SIZE-1; i++) {
                LIST_STRING.append(i+1);
                LIST_STRING.append(". ");
                LIST_STRING.append(LIST[i].toString());
                LIST_STRING.append("\n");
            }

            LIST_STRING.append(LIST_SIZE);
            LIST_STRING.append(". ");
            LIST_STRING.append(LIST[LIST_SIZE-1].toString());

            printOutput(LIST_STRING.toString());

            return 0;
        }

        if (s.length() >= 6 && s.substring(0, 5).equals("mark ")) {
            int index = Integer.parseInt(s.substring(5));
            LIST[index-1].markDone();
            printOutput("Nice! I've marked this task as done:\n  " + LIST[index-1].toString());
            return 0;
        }

        if (s.length() >= 8 && s.substring(0, 7).equals("unmark ")) {
            int index = Integer.parseInt(s.substring(7));
            LIST[index-1].markNotDone();
            printOutput("OK, I've marked this task as not done yet:\n  " + LIST[index-1].toString());
            return 0;
        }

        LIST[LIST_SIZE] = new Task(s);
        LIST_SIZE += 1;
        printOutput("added: " + s);
        return 0;
    }

    public static void main(String[] args) {
        printOutput(HELLO);
        while (command(takeInput()) == 0);
    }
}
