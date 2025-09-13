import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Pecky {



    private static ArrayList<Task> TASK_LIST = new ArrayList<Task>(100);
    private static int TASK_LIST_SIZE = 0;
    private static StringBuilder SB = new StringBuilder();

    private static Path taskFileFolder = Paths.get("./data");
    private static Path taskFile = Paths.get("./data/pecky.txt");

    private static void addTaskSilent(Task t) {
        TASK_LIST.add(t);
        TASK_LIST_SIZE += 1;
    }

    private static void addTask(Task t) {
        addTaskSilent(t);
        Ui.print("Got it. I've added this task: \n  " + t + "\nNow you have " + TASK_LIST_SIZE + " tasks in the list.");
    }

    private static void writeTaskFile() {
        SB = new StringBuilder();
        for (int i = 0; i< TASK_LIST_SIZE; i++) {
            Task task = TASK_LIST.get(i);
            SB.append(task.toTaskListString());
            SB.append("\n");
        }

        String content = SB.toString();

        try {
            Files.writeString(taskFile, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing string to file: " + e.getMessage());
        }
    }

    private static void list() {
        SB = new StringBuilder();
        SB.append("Here are the tasks in your list:\n");
        for (int i = 0; i< TASK_LIST_SIZE; i++) {
            SB.append("\n");
            SB.append(i+1);
            SB.append(". ");
            SB.append(TASK_LIST.get(i).toString());
        }

        Ui.print(SB.toString());
    }

    private static void mark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(5));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        TASK_LIST.get(index-1).markDone();
        Ui.print("Nice! I've marked this task as done:\n  " + TASK_LIST.get(index-1).toString());
    }

    private static void unmark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(7));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        TASK_LIST.get(index-1).markNotDone();
        Ui.print("OK, I've marked this task as not done yet:\n  " + TASK_LIST.get(index-1).toString());
    }

    private static void todo(String s) {
        String[] args = s.split(" ");
        if (args.length == 1) {
            Ui.print("OOPS!!! The description of a todo cannot be empty.");
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
        addTask(Deadline.createDeadline(description, by));
    }

    private static void event(String s) {
        s = s.substring(6).trim();
        String[] parts = s.split(" /from ");
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0];
        String to = timeParts[1];
        addTask(Event.createEvent(description, from, to));
    }

    private static void delete(String s) {
        int index = Integer.parseInt(s.substring(7));
        Task taskRemoved = TASK_LIST.get(index-1);
        TASK_LIST.remove(index-1);
        TASK_LIST_SIZE -= 1;
        Ui.print("     Noted. I've removed this task:\n  " + taskRemoved + "\nNow you have " + TASK_LIST_SIZE + " tasks in the list.");
    }

    private static void tasksOnDate(String s) {
        String[] args = s.split(" ");
        if (args.length == 1) {
            Ui.print("OOPS!!! You must specify a date.");
            return;
        }
        String dateString = s.substring(5);

        LocalDateTime dateTime = Task.convertStringToDate(dateString);
        if (dateTime == null) {
            Ui.print("Your date format is invalid!");
            return;
        }

        SB = new StringBuilder();
        SB.append("Here are the tasks on ");
        SB.append(dateTime.format(Task.TO_STRING_FORMATTER));
        SB.append(" :\n");

        for (int i = 0; i< TASK_LIST.size(); i++) {
            if (TASK_LIST.get(i).onDay(dateTime)) {
                SB.append("\n");
                SB.append(i+1);
                SB.append(". ");
                SB.append(TASK_LIST.get(i).toString());
            }
        }

        Ui.print(SB.toString());
    }

    private static void unknownCommand() {
        Ui.print("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    private static int command(String s) {
        String[] args = s.split(" ");

        switch (args[0]) {
            case "bye":
                Ui.bye();
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
            case "date":
                tasksOnDate(s);
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
                    Ui.print("Empty line in task file!");
                    return;
                }
                String[] args = line.split("\\|");
                Task newTask;
                if (args[0].equals("T")) {
                    newTask = new Todo(args[2]);
                } else if (args[0].equals("D")) {
                    newTask = Deadline.createDeadline(args[2], args[3]);
                } else if (args[0].equals("E")) {
                    newTask = Event.createEvent(args[2], args[3], args[4]);
                } else {
                    Ui.print("Unexpected line in task file: " + line);
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

        Ui.hello();

        while (command(Ui.getInput()) == 0);
    }
}
