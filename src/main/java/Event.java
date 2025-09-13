import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss");
        String formattedFrom = this.from.format(dateFormatter);
        String formattedTo = this.to.format(dateFormatter);
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }

    @Override
    public String toTaskListString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String formattedFrom = this.from.format(dateFormatter);
        String formattedTo = this.to.format(dateFormatter);
        return "E|" + (this.isDone ? 1 : 0) + "|" + this.description + "|" + formattedFrom + "|" + formattedTo;
    }
}