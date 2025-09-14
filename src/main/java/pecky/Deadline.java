package pecky;

import java.time.LocalDateTime;

public class Deadline extends Task {

    protected LocalDateTime by;

    private Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public static Deadline createDeadline(String description, String by) {
        LocalDateTime byDate = convertStringToDate(by);
        if (byDate == null) {
            System.out.println("/from string pattern is invalid: " + by);
            return null;
        }
        return new Deadline(description, byDate);
    }

    @Override
    public String toString() {
        String formattedDate = this.by.format(TO_STRING_FORMATTER);
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String toTaskListString() {
        String formattedDate = this.by.format(TO_TASK_LIST_STRING_FORMATTER);
        return "D|" + (this.isDone ? 1 : 0) + "|" + this.description + "|" + formattedDate;
    }

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        return dateTime.isEqual(this.by)
                || (dateTime.isAfter(this.by.minusDays(1)) && dateTime.isBefore(this.by));
    }
}
