import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm:ss");
        String formattedDate = this.by.format(dateFormatter);
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }

    @Override
    public String toTaskListString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String formattedDate = this.by.format(dateFormatter);
        return "D|" + (this.isDone ? 1 : 0) + "|" + this.description + "|" + formattedDate;
    }
}