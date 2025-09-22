package pecky;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

/**
 * Abstracts out the reading and writing of tasks to storage.
 */

public class Storage {
    private static final Path taskFileFolder = Paths.get("./data");
    private static final Path taskFile = Paths.get("./data/pecky.txt");
    private static TaskList taskList;

    /**
     * Gets the list of tasks.
     *
     * @return A TaskList representing the current list of tasks.
     */

    public static TaskList getTaskList() {
        return Storage.taskList;
    }

    /**
     * Removes the given task from the task list and updates the text file.
     *
     * @param i An integer representing the task to be removed.
     */

    public static void remove(int i) {
        Task taskRemoved = taskList.remove(i - 1);
        if (taskRemoved == null) {
            Ui.print("Removal of task unsuccessful. Check whether you input a valid number.");
            return;
        }
        writeTaskFile();
        String line1 = "Noted. I've removed this task:\n  " + taskRemoved;
        String line2 = "\nNow you have " + taskList.size() + " tasks in the list.";
        Ui.print(line1 + line2);
    }

    /**
     * Adds the given task to the task list and updates the text file.
     *
     * @param t A Task representing the task to be added.
     */

    private static void addTaskSilent(Task t) {
        taskList.add(t);
        writeTaskFile();
    }

    /**
     * Adds the given task to the task list, updates the text file,
     * and updates the user through the ui.
     *
     * @param t A Task representing the task to be added.
     */

    public static void addTask(Task t) {
        addTaskSilent(t);
        String line1 = "Got it. I've added this task: \n  " + t;
        String line2 = "\nNow you have " + taskList.size() + " tasks in the list.";
        Ui.print(line1 + line2);
    }

    /**
     * Marks the given task as completed, updates the text file,
     * and updates the user through the ui.
     *
     * @param i An integer representing the task to be marked completed.
     */


    public static void mark(int i) {
        taskList.mark(i - 1);
        writeTaskFile();
        String line1 = "Nice! I've marked this task as done:";
        String line2 = "\n  " + taskList.get(i - 1).toString();
        Ui.print(line1 + line2);
    }

    /**
     * Marks the given task as not completed, updates the text file,
     * and updates the user through the ui.
     *
     * @param i An integer representing the task to be marked not completed.
     */

    public static void unmark(int i) {
        taskList.unmark(i - 1);
        writeTaskFile();
        String line1 = "OK, I've marked this task as not done yet:";
        String line2 = "\n  " + taskList.get(i - 1).toString();
        Ui.print(line1 + line2);
    }

    private static void readInTask(String line) {
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
    }

    private static void loadLines(Stream<String> lines) {
        lines.forEach(line -> {
            if (line.isEmpty()) {
                Ui.print("Empty line in task file!");
                return;
            }
            readInTask(line);
        });
    }

    /**
     * Reads the list of tasks from the text file.
     */

    private static void loadTaskFile() {
        taskList = new TaskList();
        try (Stream<String> lines = Files.lines(taskFile)) {
            loadLines(lines);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initializes the list of tasks from the text file.
     * If either the text file or its folder is not present,
     * create the missing file or folder and initialize the empty list of tasks.
     * Runs everytime the program starts.
     */

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
        String content = taskList.toTaskListString();

        try {
            Files.writeString(taskFile, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing string to file: " + e.getMessage());
        }
    }
}
