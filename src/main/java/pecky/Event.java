package pecky;

import java.time.LocalDateTime;

/**
 * Represents an event. An <code>Event</code> object has a description,
 * a from date and a to date e.g.,
 * <code>Chemistry test, 4th August 2025 1000, 4th August 2025 1200.</code>
 */

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    private Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a new Event object.
     * If the string pattern of the from and to datetime strings is invalid,
     * a null object is returned.
     * If the from datetime is after the to datetime, a null object is returned.
     *
     * @param description Description of the event.
     * @param from The date and time of the start of the event.
     * @param to The date and time of the end of the event.
     * @return A new event object.
     */

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
            System.out.println("From date must be before the to date.");
            return null;
        }
        return new Event(description, fromDate, toDate);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Event object
     * in the format "[E] [status] description (from: date to: date)".
     * This output is useful to print to the end-user, and for debugging purposes.
     *
     * @return String representation of Event object.
     */

    @Override
    public String toString() {
        String formattedFrom = this.from.format(TO_STRING_FORMATTER);
        String formattedTo = this.to.format(TO_STRING_FORMATTER);
        String name = "[E]" + super.toString();
        String duration = "(from: " + formattedFrom + " to: " + formattedTo + ")";
        return name + " " + duration;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Event object
     * in the format "E|isDone|description|from date|to date".
     * This output is useful for reading and writing to a text file.
     *
     * @return String representation of Event object.
     */

    @Override
    public String toTaskListString() {
        String formattedFrom = this.from.format(TO_TASK_LIST_STRING_FORMATTER);
        String formattedTo = this.to.format(TO_TASK_LIST_STRING_FORMATTER);
        String name = "E|" + (this.isDone ? 1 : 0) + "|" + this.description;
        String duration = "|" + formattedFrom + "|" + formattedTo;
        return name + duration;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns true if the given dateTime is between
     * the from datetime and the to datetime, and false otherwise.
     *
     * @return A true/false boolean.
     */

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        boolean eventIsActivated = dateTime.isAfter(this.from.minusDays(1));
        boolean eventIsNotPassed = dateTime.isBefore(this.to);
        return eventIsActivated && eventIsNotPassed;
    }
}
