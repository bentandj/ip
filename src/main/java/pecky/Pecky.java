package pecky;
import java.time.LocalDateTime;

/**
 * The Pecky class is responsible for the high-level logic of the program.
 * It coordinates with the other classes so that a cohesive program can
 * be presented to the user.
 * It is also the main entry point of the program.
 */

public class Pecky {

    private StringBuilder sB = new StringBuilder();
    private TaskList taskList;
    private Storage storage;

    /**
     * Constructor for Pecky.
     */
    public Pecky() {
        this.storage = new Storage();
        this.taskList = this.storage.getTaskList();
        Ui.hello();
    }

    /**
     * Lists out all the tasks to the user.
     */

    protected void list() {
        Ui.print("Here are the tasks in your list:\n\n" + taskList);
    }

    /**
     * Marks a given task as completed.
     *
     * @param index An integer representing the task to be marked completed.
     */

    protected void mark(int index) {
        storage.mark(index);
    }

    /**
     * Marks a given task as not completed.
     *
     * @param index An integer representing the task to be marked not completed.
     */

    protected void unmark(int index) {
        storage.unmark(index);
    }

    /**
     * Adds a Todo with the given description to the task list.
     *
     * @param description A string representing the description of the Todo.
     */

    protected void todo(String description) {
        storage.addTask(new Todo(description));
    }

    /**
     * Adds a Deadline with the given description and datetime to the task list.
     *
     * @param description A string representing the description of the Deadline.
     * @param by A string representing the date and time of the Deadline.
     */

    protected void deadline(String description, String by) {
        storage.addTask(Deadline.createDeadline(description, by));
    }

    /**
     * Adds an Event with the given description and two datetimes to the task list.
     *
     * @param description A string representing the description of the Event.
     * @param from A string representing the date and time of the start of the Event.
     * @param to A string representing the date and time of the end of the Event.
     */

    protected void event(String description, String from, String to) {
        storage.addTask(Event.createEvent(description, from, to));
    }

    /**
     * Removes the given task from the task list.
     *
     * @param index An integer representing the task to be removed.
     */

    protected void delete(int index) {
        storage.remove(index - 1);
    }

    /**
     * Lists out all the tasks occurring on the given date to the user.
     *
     * @param dateTime A LocalDateTime representing the given date.
     */

    protected void tasksOnDate(LocalDateTime dateTime) {
        sB = new StringBuilder();
        sB.append("Here are the tasks on ");
        sB.append(dateTime.format(Task.TO_STRING_FORMATTER));
        sB.append(" :\n\n");
        sB.append(taskList.getTasksOnDate(dateTime));
        Ui.print(sB.toString());
    }

    /**
     * Says bye to the user and exits the program.
     */

    protected void bye() {
        Ui.bye();
        System.exit(0);
    }

    protected void find(String s) {
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

    protected void remind() {
        sB = new StringBuilder();
        sB.append("Here are the deadlines in the next 7 days in your list:\n\n");
        sB.append(taskList.getUpcomingDeadlines());

        Ui.print(sB.toString());
    }

    /**
     * Tells the user that the command that was input is unknown.
     */

    protected void unknown() {
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
        Pecky pecky = new Pecky();
        assert pecky.taskList != null;
        while (Parser.parse(pecky, Ui.getInput()) == Parser.CONTINUE);
    }
}
