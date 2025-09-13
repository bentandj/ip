import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Pecky {

    private static String HELLO = "Hello! I'm Pecky!\n" +
            "What can I do for you?";

    private static String BYE = "Bye. Hope to see you again soon!";

    private static ArrayList<Task> LIST = new ArrayList<Task>(100);
    private static int LIST_SIZE = 0;
    private static StringBuilder LIST_STRING = new StringBuilder();
    private static StringBuilder SB = new StringBuilder();

    private static Path taskFileFolder = Paths.get("./data");
    private static Path taskFile = Paths.get("./data/pecky.txt");
    // private static Path taskFile = Paths.get("./data/pecky.txt");

    private static void printOutput(String s) {
        System.out.println("____________________________________________________________");
        System.out.println(s);
        System.out.println("____________________________________________________________");
    }

    private static String takeInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void addTaskSilent(Task t) {
        LIST.add(t);
        LIST_SIZE += 1;
    }

    private static void addTask(Task t) {
        addTaskSilent(t);
        printOutput("Got it. I've added this task: \n  " + t + "\nNow you have " + LIST_SIZE + " tasks in the list.");
    }

    private static void writeTaskFile() {
        SB = new StringBuilder();
        for (int i=0; i<LIST_SIZE; i++) {
            Task task = LIST.get(i);
            SB.append(task.toTaskListString());
            SB.append("\n");
        }

        String content = SB.toString();

        try {
            Files.writeString(taskFile, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing string to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void bye() {
        printOutput(BYE);
    }

    private static void list() {
        if (LIST_SIZE == 0) {
            printOutput("");
            return;
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
    }

    private static void mark(String s) {
        int index = Integer.parseInt(s.substring(5));
        LIST.get(index-1).markDone();
        printOutput("Nice! I've marked this task as done:\n  " + LIST.get(index-1).toString());
    }

    private static void unmark(String s) {
        int index = Integer.parseInt(s.substring(7));
        LIST.get(index-1).markNotDone();
        printOutput("OK, I've marked this task as not done yet:\n  " + LIST.get(index-1).toString());
    }

    private static void todo(String s) {
        String[] args = s.split(" ");
        if (args.length == 1) {
            printOutput("OOPS!!! The description of a todo cannot be empty.");
            return;
        }
        String description = s.substring(5);
        addTask(new Todo(description));
    }

    private static void deadline(String s) {
        s = s.substring(9).trim();
        String[] parts = s.split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();
        addTask(new Deadline(description, by));
    }

    private static void event(String s) {
        s = s.substring(6).trim();
        String[] parts = s.split(" /from ");
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0];
        String to = timeParts[1];
        addTask(new Event(description, from, to));
    }

    private static void delete(String s) {
        int index = Integer.parseInt(s.substring(7));
        Task taskRemoved = LIST.get(index-1);
        LIST.remove(index-1);
        LIST_SIZE -= 1;
        printOutput("     Noted. I've removed this task:\n  " + taskRemoved + "\nNow you have " + LIST_SIZE + " tasks in the list.");
    }

    private static void unknownCommand() {
        printOutput("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    private static int command(String s) {
        String[] args = s.split(" ");

        switch (args[0]) {
            case "bye":
                bye();
                return 1;
            case "list":
                list();
                return 0;
            case "mark":
                mark(s);
                writeTaskFile();
                return 0;
            case "unmark":
                unmark(s);
                writeTaskFile();
                return 0;
            case "todo":
                todo(s);
                writeTaskFile();
                return 0;
            case "deadline":
                deadline(s);
                writeTaskFile();
                return 0;
            case "event":
                event(s);
                writeTaskFile();
                return 0;
            case "delete":
                delete(s);
                writeTaskFile();
                return 0;
            default:
                unknownCommand();
                return 0;
        }
    }

    private static void loadTaskFile() {
        try (Stream<String> lines = Files.lines(taskFile)) {
            lines.forEach(line -> {
                if (line.isEmpty()) {
                    System.out.println("Empty line in task file!");
                    return;
                }
                String[] args = line.split("\\|");
                Task newTask;
                if (args[0].equals("T")) {
                    newTask = new Todo(args[2]);
                } else if (args[0].equals("D")) {
                    newTask = new Deadline(args[2], args[3]);
                } else if (args[0].equals("E")) {
                    newTask = new Event(args[2], args[3], args[4]);
                } else {
                    System.out.println("Unexpected line in task file: " + line);
                    return;
                }

                if (args[1].equals("1")) {
                    newTask.markDone();
                }

                addTaskSilent(newTask);
            });
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
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

        loadTaskFile();
    }

    public static void main(String[] args) {
        initialize();

        printOutput(HELLO);

        while (command(takeInput()) == 0);
    }
}
