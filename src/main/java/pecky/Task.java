package pecky;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Task. A <code>Task</code> object has a description
 * and a boolean indicating whether it is done.
 * e.g., <code>Chemistry homework, false (not done).</code>
 */

public abstract class Task {
    public static final DateTimeFormatter TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss");
    public static final DateTimeFormatter TO_TASK_LIST_STRING_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected String description;
    protected boolean isDone;
    /**
     * Constructs a new Task object with the specified description.
     * By default, tasks are not marked as done when initialized.
     *
     * @param description Description of the task.
     */

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */

    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */

    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status of the task, i.e. whether it is done.
     */

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Task object
     * in the format "[status] description".
     *
     * @return String representation of the Task object.
     */

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * This is a static method to handle the conversion of a date from a String to
     * a LocalDateTime.
     * It can handle a few string formats, e.g. yyyy-M-d HHmm or d/M/yyyy.
     *
     * @param dateString A String representing the date.
     * @return A LocalDateTime representing the same date.
     */

    public static LocalDateTime convertStringToDate(String dateString) {
        String[] possibleFormatsDateTime = {"yyyy-M-d HHmm",
            "yyyy/M/d HHmm", "d-M-yyyy HHmm", "d/M/yyyy HHmm"};

        for (int i = 0; i < possibleFormatsDateTime.length; i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(possibleFormatsDateTime[i]);

            try {
                return LocalDateTime.parse(dateString, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        String[] possibleFormatsDate = {"d/M/yyyy", "d-M-yyyy", "yyyy/M/d", "yyyy-M-d"};

        for (int i = 0; i < possibleFormatsDate.length; i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(possibleFormatsDate[i]);

            try {
                LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
                return localDate.atStartOfDay();
            } catch (DateTimeParseException e) {
                continue;
            }
        }

        System.err.println("Error parsing date and time: " + dateString);
        return null;
    }

    /**
     * This is an abstract method to convert the Task to a String representation that
     * is easy to read and write to a text file.
     * This method is to be implemented by the children of the Task class.
     *
     * @return A String that can be read and written to a text file.
     */

    public abstract String toTaskListString();

    /**
     * This is an abstract method to determine whether the task falls on the given date.
     * This method is to be implemented by the children of the Task class.
     * By default, if the task does not have any date field, then
     * it's considered to not fall on any given date.
     *
     * @param dateTime A LocalDateTime representing the given date.
     * @return A boolean that is true if the task falls on the given date,
     *         and false otherwise.
     */

    public abstract boolean onDay(LocalDateTime dateTime);

    /**
     * Checks whether the string s is contained within the Task's description.
     *
     * @param s The string to be checked.
     * @return A boolean that is true if s is contained within the description,
     *         and false otherwise.
     */

    public boolean substringMatch(String s) {
        return this.description.toLowerCase().contains(s.toLowerCase());
    }
}
