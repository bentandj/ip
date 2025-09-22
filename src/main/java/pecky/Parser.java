package pecky;

import java.time.LocalDateTime;

/**
 * Parses user commands, prints helpful error messages, and redirects
 * execution to the relevant Pecky functions otherwise.
 */

public class Parser {

    public static final int EXIT = 1;
    public static final int CONTINUE = 0;

    /**
     * Parses user commands.
     * If user input is invalid, helpful error messages are output to the user.
     * Else, the relevant functions in Pecky are called.
     * It returns 1 to signal the termination of the program, and 0 otherwise.
     *
     * @param s A string representing the user command.
     * @return The integer 1 if the program should be terminated, and 0 otherwise.
     */

    public static int parse(Pecky pecky, String s) {
        String[] args = s.split(" ");

        switch (args[0]) {
        case "bye":
            Parser.bye(pecky);
            return Parser.EXIT;
        default:
            return parseTaskListCommands(pecky, s, args);
        }
    }

    /**
     * Second step in parsing user commands.
     * If user input is invalid, helpful error messages are output to the user.
     * Else, the relevant functions in Pecky are called.
     * It returns 1 to signal the termination of the program, and 0 otherwise.
     *
     * @param s A string representing the user command.
     * @return The integer 1 if the program should be terminated, and 0 otherwise.
     */

    public static int parseTaskListCommands(Pecky pecky, String s, String[] args) {
        switch (args[0]) {
        case "list":
            Parser.list(pecky);
            return Parser.CONTINUE;
        case "mark":
            Parser.mark(pecky, s);
            return Parser.CONTINUE;
        case "unmark":
            Parser.unmark(pecky, s);
            return Parser.CONTINUE;
        case "delete":
            Parser.delete(pecky, s);
            return Parser.CONTINUE;
        case "date":
            Parser.date(pecky, s, args);
            return Parser.CONTINUE;
        case "find":
            Parser.find(pecky, s, args);
            return Parser.CONTINUE;
        case "remind":
            Parser.remind(pecky);
            return Parser.CONTINUE;
        default:
            return Parser.parseAddCommands(pecky, s, args);
        }
    }

    /**
     * Third step in parsing user commands.
     * If user input is invalid, helpful error messages are output to the user.
     * Else, the relevant functions in Pecky are called.
     * It returns 1 to signal the termination of the program, and 0 otherwise.
     *
     * @param s A string representing the user command.
     * @return The integer 1 if the program should be terminated, and 0 otherwise.
     */

    public static int parseAddCommands(Pecky pecky, String s, String[] args) {
        switch (args[0]) {
        case "todo":
            Parser.todo(pecky, s, args);
            return Parser.CONTINUE;
        case "deadline":
            Parser.deadline(pecky, s);
            return Parser.CONTINUE;
        case "event":
            Parser.event(pecky, s);
            return Parser.CONTINUE;
        default:
            Parser.unknown(pecky);
            return Parser.CONTINUE;
        }
    }

    private static void bye(Pecky pecky) {
        pecky.bye();
    }

    private static void list(Pecky pecky) {
        pecky.list();
    }

    private static void mark(Pecky pecky, String s) {
        if (s.length() <= 5) {
            Ui.print("OOPS!!! You must choose a task to mark completed.");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(s.substring(5));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        try {
            pecky.mark(index);
        } catch (Exception e) {
            Ui.print("Check that the index you input is the correct index!");
        }
    }

    private static void unmark(Pecky pecky, String s) {
        if (s.length() <= 7) {
            Ui.print("OOPS!!! You must choose a task to mark uncompleted.");
            return;
        }
        int index;
        try {
            index = Integer.parseInt(s.substring(7));
        } catch (NumberFormatException e) {
            Ui.print("Must be integer! " + e.getMessage());
            return;
        }
        try {
            pecky.unmark(index);
        } catch (Exception e) {
            Ui.print("Check that the index you input is the correct index!");
        }
    }

    private static void todo(Pecky pecky, String s, String[] args) {
        if (args.length == 1) {
            Ui.print("OOPS!!! The description of a todo cannot be empty.");
            return;
        }
        String description = s.substring(5);
        pecky.todo(description);
    }

    private static void deadline(Pecky pecky, String s) {
        if (s.length() <= 9) {
            Ui.print("OOPS!!! The syntax of the command is 'deadline [description] /by [date / datetime]'");
            return;
        }
        try {
            s = s.substring(9).trim();
            String[] parts = s.split(" /by ");
            String description = parts[0].trim();
            String by = parts[1].trim();
            pecky.deadline(description, by);
        } catch (Exception e) {
            Ui.print("OOPS!!! The syntax of the command is 'deadline [description] /by [date / datetime]'");
        }
    }

    private static void event(Pecky pecky, String s) {
        if (s.length() <= 6) {
            Ui.print("OOPS!!! The syntax of the command is "
                    + "'event [description] /from [date / datetime] /to [date / datetime]'");
            return;
        }

        try {
            s = s.substring(6).trim();
            String[] parts = s.split(" /from ");
            String description = parts[0].trim();
            String[] timeParts = parts[1].split(" /to ");
            String from = timeParts[0];
            String to = timeParts[1];
            pecky.event(description, from, to);
        } catch (Exception e) {
            Ui.print("OOPS!!! The syntax of the command is "
                    + "'event [description] /from [date / datetime] /to [date / datetime]'");
        }
    }

    private static void delete(Pecky pecky, String s) {
        if (s.length() <= 7) {
            Ui.print("OOPS!!! You must choose a task (input its index as an integer) to delete!");
            return;
        }
        try {
            int index = Integer.parseInt(s.substring(7));
            pecky.delete(index);
        } catch (Exception e) {
            Ui.print("OOPS!!! The syntax of the command is "
                    + "'delete [integer index]'");
        }
    }

    private static void date(Pecky pecky, String s, String[] args) {
        if (args.length == 1) {
            Ui.print("OOPS!!! You must specify a date.");
            return;
        }
        assert s.length() > 5;
        String dateString = s.substring(5);

        LocalDateTime dateTime = Task.convertStringToDate(dateString);
        if (dateTime == null) {
            Ui.print("Your date format is invalid!");
            return;
        }
        pecky.tasksOnDate(dateTime);
    }

    private static void find(Pecky pecky, String s, String[] args) {
        if (args.length == 1) {
            Ui.print("OOPS!!! You must specify what you're trying to find.");
            return;
        }
        assert s.length() > 5;
        String find = s.substring(5);
        pecky.find(find);
    }

    private static void remind(Pecky pecky) {
        pecky.remind();
    }

    private static void unknown(Pecky pecky) {
        pecky.unknown();
    }
}
