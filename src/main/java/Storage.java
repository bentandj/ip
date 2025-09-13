import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Storage {
    private static Path taskFileFolder = Paths.get("./data");
    private static Path taskFile = Paths.get("./data/pecky.txt");
    private static ArrayList<Task> TASK_LIST = new ArrayList<Task>(100);
    private static int TASK_LIST_SIZE = 0;

    public static int num() {
        return TASK_LIST_SIZE;
    }

    public static Task get(int i) {
        if (i < 0 || i >= TASK_LIST_SIZE) {
            return null;
        }
        return TASK_LIST.get(i);
    }

    public static void remove(int i) {
        if (i < 0 || i >= TASK_LIST_SIZE) {
            return;
        }
        Task taskRemoved = TASK_LIST.get(i-1);
        TASK_LIST.remove(i - 1);
        TASK_LIST_SIZE -= 1;
        writeTaskFile();
        Ui.print("Noted. I've removed this task:\n  " + taskRemoved + "\nNow you have " + TASK_LIST_SIZE + " tasks in the list.");
    }

    private static void addTaskSilent(Task t) {
        TASK_LIST.add(t);
        TASK_LIST_SIZE += 1;
        writeTaskFile();
    }

    public static void addTask(Task t) {
        addTaskSilent(t);
        Ui.print("Got it. I've added this task: \n  " + t + "\nNow you have " + TASK_LIST_SIZE + " tasks in the list.");
    }

    public static void mark(int i) {
        TASK_LIST.get(i-1).markDone();
        writeTaskFile();
        Ui.print("Nice! I've marked this task as done:\n  " + TASK_LIST.get(i-1).toString());
    }

    public static void unmark(int i) {
        TASK_LIST.get(i-1).markNotDone();
        writeTaskFile();
        Ui.print("OK, I've marked this task as not done yet:\n  " + TASK_LIST.get(i-1).toString());
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

    public static void initialize() {
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

    private static void writeTaskFile() {
        StringBuilder SB = new StringBuilder();
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
}
