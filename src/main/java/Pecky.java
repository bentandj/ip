import java.time.LocalDateTime;

public class Pecky {

    private static StringBuilder SB = new StringBuilder();

    protected static void list() {
        SB = new StringBuilder();
        SB.append("Here are the tasks in your list:\n");
        for (int i = 0; i < Storage.num(); i++) {
            SB.append("\n");
            SB.append(i+1);
            SB.append(". ");
            SB.append(Storage.get(i).toString());
        }

        Ui.print(SB.toString());
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
        SB.append(" :\n");

        for (int i = 0; i < Storage.num(); i++) {
            if (Storage.get(i).onDay(dateTime)) {
                SB.append("\n");
                SB.append(i+1);
                SB.append(". ");
                SB.append(Storage.get(i).toString());
            }
        }

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
        Ui.hello();
        while (Parser.parse(Ui.getInput()) == 0);
    }
}
