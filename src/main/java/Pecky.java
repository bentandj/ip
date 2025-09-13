import java.time.LocalDateTime;

public class Pecky {

    private static StringBuilder SB = new StringBuilder();
    private static TaskList taskList;

    protected static void list() {
        Ui.print("Here are the tasks in your list:\n\n" + taskList);
    }

    protected static void mark(int index) {
        Storage.mark(index);
    }

    protected static void unmark(int index) {
        Storage.unmark(index);
    }

    protected static void todo(String description) {
        Storage.addTask(new Todo(description));
    }

    protected static void deadline(String description, String by) {
        Storage.addTask(Deadline.createDeadline(description, by));
    }

    protected static void event(String description, String from, String to) {
        Storage.addTask(Event.createEvent(description, from, to));
    }

    protected static void delete(int index) {
        Storage.remove(index-1);
    }

    protected static void tasksOnDate(LocalDateTime dateTime) {
        SB = new StringBuilder();
        SB.append("Here are the tasks on ");
        SB.append(dateTime.format(Task.TO_STRING_FORMATTER));
        SB.append(" :\n\n");

        TaskList tasksOnDate = new TaskList();
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).onDay(dateTime)) {
                tasksOnDate.add(taskList.get(i));
            }
        }
        SB.append(tasksOnDate);

        Ui.print(SB.toString());
    }

    protected static void bye() {
        Ui.bye();
        System.exit(0);
    }

    protected static void unknown() {
        Ui.unknown();
    }

    public static void main(String[] args) {
        Storage.initialize();
        taskList = Storage.getTaskList();
        Ui.hello();
        while (Parser.parse(Ui.getInput()) == 0);
    }
}
