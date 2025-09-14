package pecky;

import java.time.LocalDateTime;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toTaskListString() {
        return "T|" + (this.isDone ? 1 : 0) + "|" + this.description;
    }

    @Override
    public boolean onDay(LocalDateTime dateTime) {
        return false;
    }
}
