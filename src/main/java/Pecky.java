import java.time.LocalDateTime;

public class Pecky {

    private static StringBuilder SB = new StringBuilder();

    private static void list() {
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

    private static void mark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(5));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        Storage.mark(index);
    }

    private static void unmark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(7));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        Storage.unmark(index);
    }

    private static void todo(String s) {
        String[] args = s.split(" ");
        if (args.length == 1) {
            Ui.print("OOPS!!! The description of a todo cannot be empty.");
            return;
        }
        String description = s.substring(5);
        Storage.addTask(new Todo(description));
    }

    private static void deadline(String s) {
        s = s.substring(9).trim();
        String[] parts = s.split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();
        Storage.addTask(Deadline.createDeadline(description, by));
    }

    private static void event(String s) {
        s = s.substring(6).trim();
        String[] parts = s.split(" /from ");
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0];
        String to = timeParts[1];
        Storage.addTask(Event.createEvent(description, from, to));
    }

    private static void delete(String s) {
        int index = Integer.parseInt(s.substring(7));
        Storage.remove(index-1);
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
                return 0;
            case "unmark":
                unmark(s);
                return 0;
            case "todo":
                todo(s);
                return 0;
            case "deadline":
                deadline(s);
                return 0;
            case "event":
                event(s);
                return 0;
            case "delete":
                delete(s);
                return 0;
            case "date":
                tasksOnDate(s);
                return 0;
            default:
                Ui.unknown();
                return 0;
        }
    }

    public static void main(String[] args) {
        Storage.initialize();
        Ui.hello();
        while (command(Ui.getInput()) == 0);
    }
}
