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

    private static class Todo extends Task {

        public Todo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    private static class Deadline extends Task {

        protected String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    private static class Event extends Task {
        protected String from;
        protected String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
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

    private static void addTask(Task t) {
        LIST[LIST_SIZE] = t;
        LIST_SIZE += 1;
        printOutput("Got it. I've added this task: \n  " + t + "\nNow you have " + LIST_SIZE + " tasks in the list.");
    }

    private static int command(String s) {
        String[] args = s.split(" ");

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

        if (args[0].equals("mark")) {
            int index = Integer.parseInt(s.substring(5));
            LIST[index-1].markDone();
            printOutput("Nice! I've marked this task as done:\n  " + LIST[index-1].toString());
            return 0;
        }

        if (args[0].equals("unmark")) {
            int index = Integer.parseInt(s.substring(7));
            LIST[index-1].markNotDone();
            printOutput("OK, I've marked this task as not done yet:\n  " + LIST[index-1].toString());
            return 0;
        }

        if (args[0].equals("todo")) {
            addTask(new Todo(s.substring(5)));
            return 0;
        }

        if (s.length() >= 6 && s.substring(0, 5).equals("todo ")) {
            addTask(new Todo(s));
        }

        if (args[0].equals("deadline")) {
            s = s.substring(9).trim();
            String[] parts = s.split(" /by ");
            String description = parts[0].trim();
            String by = parts[1].trim();
            addTask(new Deadline(description, by));
            return 0;
        }


        if (args[0].equals("event")) {
            s = s.substring(6).trim();
            String[] parts = s.split(" /from ");
            String description = parts[0].trim();
            String[] timeParts = parts[1].split(" /to ");
            addTask(new Event(description, timeParts[0].trim(), timeParts[1].trim()));
            return 0;
        }

        addTask(new Task(s));
        return 0;
    }

    public static void main(String[] args) {
        printOutput(HELLO);
        while (command(takeInput()) == 0);
    }
}
