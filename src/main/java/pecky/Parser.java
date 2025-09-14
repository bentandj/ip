package pecky;

import java.time.LocalDateTime;

public class Parser {

    public static int parse(String s) {
        String[] args = s.split(" ");

        switch (args[0]) {
            case "bye":
                Parser.bye();
                return 1;
            case "list":
                Parser.list();
                return 0;
            case "mark":
                Parser.mark(s);
                return 0;
            case "unmark":
                Parser.unmark(s);
                return 0;
            case "todo":
                Parser.todo(s, args);
                return 0;
            case "deadline":
                Parser.deadline(s);
                return 0;
            case "event":
                Parser.event(s);
                return 0;
            case "delete":
                Parser.delete(s);
                return 0;
            case "date":
                Parser.date(s, args);
                return 0;
            default:
                Parser.unknown();
                return 0;
        }
    }

    private static void bye() {
        Pecky.bye();
    }

    private static void list() {
        Pecky.list();
    }

    private static void mark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(5));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        Pecky.mark(index);
    }

    private static void unmark(String s) {
        int index;
        try {
            index = Integer.parseInt(s.substring(7));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        Pecky.unmark(index);
    }

    private static void todo(String s, String[] args) {
        if (args.length == 1) {
            Ui.print("OOPS!!! The description of a todo cannot be empty.");
            return;
        }
        String description = s.substring(5);
        Pecky.todo(description);
    }

    private static void deadline(String s) {
        s = s.substring(9).trim();
        String[] parts = s.split(" /by ");
        String description = parts[0].trim();
        String by = parts[1].trim();
        Pecky.deadline(description, by);
    }

    private static void event(String s) {
        s = s.substring(6).trim();
        String[] parts = s.split(" /from ");
        String description = parts[0].trim();
        String[] timeParts = parts[1].split(" /to ");
        String from = timeParts[0];
        String to = timeParts[1];
        Pecky.event(description, from, to);
    }

    private static void delete(String s) {
        int index = Integer.parseInt(s.substring(7));
        Pecky.delete(index);
    }

    private static void date(String s, String[] args) {
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
        Pecky.tasksOnDate(dateTime);
    }

    private static void unknown() {
        Pecky.unknown();
    }
}
