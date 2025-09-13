import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> taskArrayList;
    private static final int DEFAULT_CAPACITY = 10;

    public TaskList() {
        taskArrayList = new ArrayList<Task>(DEFAULT_CAPACITY);
    }

    public void add(Task task) {
        taskArrayList.add(task);
    }

    public Task remove(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        Task taskRemoved = taskArrayList.get(i);
        taskArrayList.remove(i);
        return taskRemoved;
    }

    public int size() {
        return taskArrayList.size();
    }

    public Task get(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return taskArrayList.get(i);
    }

    public void mark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markDone();
    }

    public void unmark(int i) {
        if (i < 0 || i >= size()) {
            return;
        }
        taskArrayList.get(i).markNotDone();
    }

    @Override
    public String toString() {
        StringBuilder SB = new StringBuilder();
        for (int i = 0; i < size() - 1; i++) {
            SB.append(i+1);
            SB.append(". ");
            SB.append(taskArrayList.get(i));
            SB.append("\n");
        }
        SB.append(size());
        SB.append(". ");
        SB.append(taskArrayList.get(size() - 1));
        return SB.toString();
    }

    public String toTaskListString() {
        StringBuilder SB = new StringBuilder();
        for (int i = 0; i < taskArrayList.size(); i++) {
            Task task = taskArrayList.get(i);
            SB.append(task.toTaskListString());
            SB.append("\n");
        }
        return SB.toString();
    }
}
