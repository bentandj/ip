package pecky;

import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    private Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static Event createEvent(String description, String from, String to) {
        LocalDateTime fromDate = convertStringToDate(from);
        if (fromDate == null) {
            System.out.println("/from string pattern is invalid: " + from);
            return null;
        }
        LocalDateTime toDate = convertStringToDate(to);
        if (toDate == null) {
            System.out.println("/to string pattern is invalid: " + to);
            return null;
        }
        if (fromDate.isAfter(toDate)) {
            System.out.println("From date must be after to date.");
            return null;
        }
        return new Event(description, fromDate, toDate);
    }

    @Override
    public String toString() {
        String formattedFrom = this.from.format(TO_STRING_FORMATTER);
        String formattedTo = this.to.format(TO_STRING_FORMATTER);
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    @Override
    public String toTaskListString() {
        String formattedFrom = this.from.format(TO_TASK_LIST_STRING_FORMATTER);
        String formattedTo = this.to.format(TO_TASK_LIST_STRING_FORMATTER);
        return "E|" + (this.isDone ? 1 : 0) + "|" + this.description + "|" + formattedFrom + "|" + formattedTo;
    }

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        return dateTime.isAfter(this.from.minusDays(1)) && dateTime.isBefore(this.to);
    }
}