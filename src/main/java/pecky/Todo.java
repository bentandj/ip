package pecky;

import java.time.LocalDateTime;

/**
 * Represents a Todo. A <code>Todo</code> object has a description
 * e.g., <code>Chemistry homework</code>.
 */

public class Todo extends Task {

    /**
     * Constructs a new Todo object with the specified description.
     *
     * @param description Description of the task.
     */

    public Todo(String description) {
        super(description);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Todo object
     * in the format "[T] [status] description".
     *
     * @return String representation of the Todo object.
     */

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns a string representation of the Todo object
     * in the format "T|isDone|description".
     * This format is easy to read and write to a text file.
     *
     * @return String representation of the Todo object for easy I/O.
     */

    @Override
    public String toTaskListString() {
        int done = this.isDone ? DONE : NOT_DONE;
        return "T|" + done + "|" + this.description;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This method returns false as a Todo has no specified date and hence
     * does not fall on any specific date.
     *
     * @return false.
     */

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        return false;
    }
}
