import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pecky {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";

    private static String BYE = "Bye. Hope to see you again soon!";

    private static ArrayList<Task> LIST = new ArrayList<Task>(100);
    private static int LIST_SIZE = 0;
    private static StringBuilder LIST_STRING = new StringBuilder();

    private static Path taskFileFolder = Paths.get("./data");
    private static Path taskFile = Paths.get("./data/pecky.txt");

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
        LIST.add(t);
        LIST_SIZE += 1;
        printOutput("Got it. I've added this task: \n  " + t + "\nNow you have " + LIST_SIZE + " tasks in the list.");
    }

    private static void deleteTask(int t) {
        Task taskRemoved = LIST.get(t-1);
        LIST.remove(t-1);
        LIST_SIZE -= 1;
        printOutput("     Noted. I've removed this task:\n  " + taskRemoved + "\nNow you have " + LIST_SIZE + " tasks in the list.");
    }

    private static void updateTaskFile() {
        
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
                LIST_STRING.append(LIST.get(i).toString());
                LIST_STRING.append("\n");
            }

            LIST_STRING.append(LIST_SIZE);
            LIST_STRING.append(". ");
            LIST_STRING.append(LIST.get(LIST_SIZE-1).toString());

            printOutput(LIST_STRING.toString());

            return 0;
        }

        if (args[0].equals("mark")) {
            int index = Integer.parseInt(s.substring(5));
            LIST.get(index-1).markDone();
            printOutput("Nice! I've marked this task as done:\n  " + LIST.get(index-1).toString());
            return 0;
        }

        if (args[0].equals("unmark")) {
            int index = Integer.parseInt(s.substring(7));
            LIST.get(index-1).markNotDone();
            printOutput("OK, I've marked this task as not done yet:\n  " + LIST.get(index-1).toString());
            return 0;
        }

        if (args[0].equals("todo")) {
            if (args.length == 1) {
                printOutput("OOPS!!! The description of a todo cannot be empty.");
                return 0;
            }
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

        if (args[0].equals("delete")) {
            int index = Integer.parseInt(s.substring(7));
            deleteTask(index);
            return 0;
        }

        printOutput("OOPS!!! I'm sorry, but I don't know what that means :-(");
        return 0;
    }

    private static void initialize() {
        try {
            Files.createDirectories(taskFileFolder);
        } catch (IOException e) {
            System.err.println("Failed to create folder: " + e.getMessage());
        }

        try {
            Files.createFile(taskFile);
        } catch (FileAlreadyExistsException e) {
            // no need to do anything
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initialize();

        printOutput(HELLO);

        while (command(takeInput()) == 0);
    }
}
