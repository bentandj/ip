package pecky;

import java.time.LocalDateTime;

/**
 * Represents a deadline. A <code>Deadline</code> object has a description
 * and a date e.g., <code>Chemistry homework, 3rd August 2025 2359</code>.
 */

public class Deadline extends Task {

    protected LocalDateTime by;

    private Deadline(String description, LocalDateTime by) {
        super(description);
        assert by != null;
        this.by = by;
    }

    /**
     * Returns a new Deadline object.
     * If the string pattern of the date and time is invalid, a null object is returned.
     *
     * @param description Description of the deadline.
     * @param by The date and time of the deadline.
     * @return A new deadline object.
     */

    public static Deadline createDeadline(String description, String by) {
        LocalDateTime byDate = convertStringToDate(by);
        if (byDate == null) {
            Ui.print("/by string pattern is invalid: " + by);
            return null;
        }
        return new Deadline(description, byDate);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Deadline object
     * in the format "[D] [status] description (by: date)".
     * This output is useful to print to the end-user, and for debugging purposes.
     *
     * @return String representation of Deadline object.
     */

    @Override
    public String toString() {
        String formattedDate = this.by.format(TO_STRING_FORMATTER);
        String name = "[D]" + super.toString();
        String by = "(by: " + formattedDate + ")";
        return name + " " + by;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Deadline object
     * in the format "D|isDone|description|date".
     * This output is useful for reading and writing to a text file.
     *
     * @return String representation of Deadline object.
     */

    @Override
    public String toTaskListString() {
        String formattedDate = this.by.format(TO_TASK_LIST_STRING_FORMATTER);
        int done = this.isDone ? DONE : NOT_DONE;
        String name = "D|" + done + "|" + this.description;
        String by = "|" + formattedDate;
        return name + by;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns true if the deadline is occurring within 0 to 24 hours after the
     * provided dateTime, and false otherwise.
     *
     * @return True if the deadline is occurring within 24 hours of dateTime, and false otherwise.
     */

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        boolean onTheDot = dateTime.isEqual(this.by);
        boolean deadlineIsActivated = dateTime.isAfter(this.by.minusDays(1));
        boolean deadlineIsNotPassed = dateTime.isBefore(this.by);
        boolean deadlineIsActive = deadlineIsActivated && deadlineIsNotPassed;
        return onTheDot || deadlineIsActive;
    }

    /**
     * This method returns true if the deadline is occurring within the next 7 days,
     * and false otherwise.
     *
     * @return True if the deadline is occurring within the next 7 days, and false otherwise.
     */

    public boolean upcoming() {
        LocalDateTime currentTime = LocalDateTime.now();
        boolean onTheDot = currentTime.isEqual(this.by);
        boolean deadlineIsActivated = currentTime.isAfter(this.by.minusDays(7));
        boolean deadlineIsNotPassed = currentTime.isBefore(this.by);
        boolean deadlineIsActive = deadlineIsActivated && deadlineIsNotPassed;
        return onTheDot || deadlineIsActive;
    }
}
