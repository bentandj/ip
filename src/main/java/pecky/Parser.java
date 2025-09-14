package pecky;

import java.time.LocalDateTime;

/**
 * Parses user commands, prints helpful error messages, and redirects
 * execution to the relevant Pecky functions otherwise.
 */

public class Parser {

    /**
     * Parses user commands.
     * If user input is invalid, helpful error messages are output to the user.
     * Else, the relevant functions in Pecky are called.
     * It returns 1 to signal the termination of the program, and 0 otherwise.
     *
     * @param s A string representing the user command.
     * @return The integer 1 if the program should be terminated, and 0 otherwise.
     */

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
        case "find":
            Parser.find(s, args);
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

    private static void find(String s, String[] args) {
        if (args.length == 1) {
            Ui.print("OOPS!!! You must specify what you're trying to find.");
            return;
        }
        String find = s.substring(5);
        Pecky.find(find);
    }

    private static void unknown() {
        Pecky.unknown();
    }
}
