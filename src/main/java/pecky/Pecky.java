package pecky;
import java.time.LocalDateTime;

/**
 * The Pecky class is responsible for the high-level logic of the program.
 * It coordinates with the other classes so that a cohesive program can
 * be presented to the user.
 * It is also the main entry point of the program.
 */

public class Pecky {

    private static StringBuilder sB = new StringBuilder();
    private static TaskList taskList;

    /**
     * Lists out all the tasks to the user.
     */

    protected static void list() {
        Ui.print("Here are the tasks in your list:\n\n" + taskList);
    }

    /**
     * Marks a given task as completed.
     *
     * @param index An integer representing the task to be marked completed.
     */

    protected static void mark(int index) {
        Storage.mark(index);
    }

    /**
     * Marks a given task as not completed.
     *
     * @param index An integer representing the task to be marked not completed.
     */

    protected static void unmark(int index) {
        Storage.unmark(index);
    }

    /**
     * Adds a Todo with the given description to the task list.
     *
     * @param description A string representing the description of the Todo.
     */

    protected static void todo(String description) {
        Storage.addTask(new Todo(description));
    }

    /**
     * Adds a Deadline with the given description and datetime to the task list.
     *
     * @param description A string representing the description of the Deadline.
     * @param by A string representing the date and time of the Deadline.
     */

    protected static void deadline(String description, String by) {
        Storage.addTask(Deadline.createDeadline(description, by));
    }

    /**
     * Adds an Event with the given description and two datetimes to the task list.
     *
     * @param description A string representing the description of the Event.
     * @param from A string representing the date and time of the start of the Event.
     * @param to A string representing the date and time of the end of the Event.
     */

    protected static void event(String description, String from, String to) {
        Storage.addTask(Event.createEvent(description, from, to));
    }

    /**
     * Removes the given task from the task list.
     *
     * @param index An integer representing the task to be removed.
     */

    protected static void delete(int index) {
        Storage.remove(index - 1);
    }

    /**
     * Lists out all the tasks occurring on the given date to the user.
     *
     * @param dateTime A LocalDateTime representing the given date.
     */

    protected static void tasksOnDate(LocalDateTime dateTime) {
        sB = new StringBuilder();
        sB.append("Here are the tasks on ");
        sB.append(dateTime.format(Task.TO_STRING_FORMATTER));
        sB.append(" :\n\n");

        TaskList tasksOnDate = new TaskList();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).onDay(dateTime)) {
                tasksOnDate.add(taskList.get(i));
            }
        }
        sB.append(tasksOnDate);

        Ui.print(sB.toString());
    }

    /**
     * Says bye to the user and exits the program.
     */

    protected static void bye() {
        Ui.bye();
        System.exit(0);
    }

    protected static void find(String s) {
        sB = new StringBuilder();
        sB.append("Here are the matching tasks in your list:\n\n");

        TaskList matchingTasks = new TaskList();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).substringMatch(s)) {
                matchingTasks.add(taskList.get(i));
            }
        }
        sB.append(matchingTasks);

        Ui.print(sB.toString());
    }

    /**
     * Tells the user that the command that was input is unknown.
     */

    protected static void unknown() {
        Ui.unknown();
    }

    /**
     * This is the main entry point of the application.
     * It reads in the list of tasks from storage and says hello to the user.
     * It runs the main logic where it constantly waits for and receives user input,
     * parses it, and executes the relevant commands.
     *
     * @param args Command-line arguments passed to the program.
     */

    public static void main(String[] args) {
        Storage.initialize();
        taskList = Storage.getTaskList();
        Ui.hello();
        while (Parser.parse(Ui.getInput()) == 0);
    }

    public static void initialize() {
        Storage.initialize();
        taskList = Storage.getTaskList();
        Ui.hello();
    }
}
