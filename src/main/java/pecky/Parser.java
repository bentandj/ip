package pecky;

import java.time.LocalDateTime;

public class Parser {

    public static int parse(String s) {
        String[] args = s.split(" ");

        int index;
        String description, by, from, to;
        String[] parts, timeParts;

        switch (args[0]) {
            case "bye":
                Pecky.bye();
                return 1;
            case "list":
                Pecky.list();
                return 0;
            case "mark":
                try {
                    index = Integer.parseInt(s.substring(5));
                } catch (NumberFormatException e) {
                    Ui.print("Must be integer! " + e.getMessage());
                    return 0;
                }
                Pecky.mark(index);
                return 0;
            case "unmark":
                try {
                    index = Integer.parseInt(s.substring(7));
                } catch (NumberFormatException e) {
                    Ui.print("Must be integer! " + e.getMessage());
                    return 0;
                }
                Pecky.unmark(index);
                return 0;
            case "todo":
                if (args.length == 1) {
                    Ui.print("OOPS!!! The description of a todo cannot be empty.");
                    return 0;
                }
                description = s.substring(5);
                Pecky.todo(description);
                return 0;
            case "deadline":
                s = s.substring(9).trim();
                parts = s.split(" /by ");
                description = parts[0].trim();
                by = parts[1].trim();
                Pecky.deadline(description, by);
                return 0;
            case "event":
                s = s.substring(6).trim();
                parts = s.split(" /from ");
                description = parts[0].trim();
                timeParts = parts[1].split(" /to ");
                from = timeParts[0];
                to = timeParts[1];
                Pecky.event(description, from, to);
                return 0;
            case "delete":
                index = Integer.parseInt(s.substring(7));
                Pecky.delete(index);
                return 0;
            case "date":
                if (args.length == 1) {
                    Ui.print("OOPS!!! You must specify a date.");
                    return 0;
                }
                String dateString = s.substring(5);

                LocalDateTime dateTime = Task.convertStringToDate(dateString);
                if (dateTime == null) {
                    Ui.print("Your date format is invalid!");
                    return 0;
                }
                Pecky.tasksOnDate(dateTime);
                return 0;
            case "find":
                if (args.length == 1) {
                    Ui.print("OOPS!!! You must specify what you're trying to find.");
                    return 0;
                }
                String find = s.substring(5);
                Pecky.find(find);
                return 0;
            default:
                Pecky.unknown();
                return 0;
        }
    }
}
