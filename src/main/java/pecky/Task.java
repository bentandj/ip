package pecky;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public static DateTimeFormatter TO_STRING_FORMATTER = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss");
    public static DateTimeFormatter TO_TASK_LIST_STRING_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public static LocalDateTime convertStringToDate(String dateString) {
        String[] possibleFormatsDateTime = {"yyyy-M-d HHmm", "yyyy/M/d HHmm",
                "d-M-yyyy HHmm", "d/M/yyyy HHmm"};

        for (int i=0; i<possibleFormatsDateTime.length; i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(possibleFormatsDateTime[i]);

            try {
                return LocalDateTime.parse(dateString, dateTimeFormatter);
            } catch (DateTimeParseException e) {

            }
        }

        String[] possibleFormatsDate = {"d/M/yyyy", "d-M-yyyy", "yyyy/M/d", "yyyy-M-d"};

        for (int i=0; i<possibleFormatsDate.length; i++) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(possibleFormatsDate[i]);

            try {
                LocalDate localDate =  LocalDate.parse(dateString, dateTimeFormatter);
                return localDate.atStartOfDay();
            } catch (DateTimeParseException e) {

            }
        }

        System.err.println("Error parsing date and time: " + dateString);
        return null;
    }

    public abstract String toTaskListString();

    public abstract boolean onDay(LocalDateTime dateTime);
}